package com.tcssj.mbjmb.net

import android.text.TextUtils
import com.tcssj.mbjmb.net.bean.ErrorData
import com.google.gson.Gson
import com.tcssj.mbjmb.EmiApplication
import io.reactivex.Observer
import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * 复写订阅
 */
abstract class BaseObserver<T> : Observer<T> {
    override fun onError(e: Throwable) {
        var errorData = ErrorData("发生未知异常！", -2)
        if (e is SocketTimeoutException) {
            errorData = ErrorData("网络超时，请稍后再试", -1)
        } else if (e is HttpException) {
            val code = e.code()
            if (code in 200..299) { // 成功-实际上不包括
                errorData = ErrorData(e.message, code)
            } else if (401 == code) { // 服务端要求401强制登录
                errorData = ErrorData("登录失效，请重新登录", code)
                notifyTokenInvalidate()
            } else if (code in 500..599) { // 服务端异常
                errorData = ErrorData("服务端异常", code)
            } else {
                val responseBody = e.response()?.errorBody()
                if (responseBody != null) {
                    errorData = try {
                        val body = responseBody.string()
                        Gson().fromJson(body, ErrorData::class.java)
                    } catch (e1: Exception) {
                        ErrorData(e1.message, 10)
                    } finally {
                        responseBody.close()
                    }
                }
            }
        } else {
            errorData = ErrorData(e.message, 10)
        }
        if (TextUtils.isEmpty(errorData.msg)) errorData.msg = "发生未知异常！"
        onError(errorData)
    }

//    override fun onSubscribe(d: Disposable) {
//        onSubscribe(d as CompositeDisposable)
//    }

    protected abstract fun onError(errorData: ErrorData)

    //    protected abstract fun onSubscribe(d: CompositeDisposable)
    override fun onComplete() {}
    open fun notifyTokenInvalidate() {
        // 验证token是否过期
    }
}