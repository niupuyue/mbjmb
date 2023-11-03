package com.tcssj.mbjmb.net.exception

import android.net.ParseException
import cn.emilife.emiapp.net.exception.AppException
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.tcssj.mbjmb.net.bean.BaseApiResponseBean
import com.tcssj.mbjmb.net.enums.ErrorEnum
import com.tcssj.mbjmb.util.GsonUtil
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

/**
 * @author:Niu Puyue
 * e-mail:niupuyue@aliyun.com
 * desc: 根据异常返回相对应的错误信息
 */
object ExceptionHandle {

    fun handleException(e: Throwable?): AppException {
        val ex: AppException
        e?.let {
            when (it) {
                is HttpException -> {
                    val errJson = it.response()?.errorBody()?.string() ?: ""
                    ex = if (GsonUtil.isJson(errJson)) {
                        // 将错误信息转换成实体对象
                        val error = Gson().fromJson(
                            errJson,
                            BaseApiResponseBean::class.java
                        )
                        AppException(ErrorEnum.NETWORK_ERROR.getKey(), error.message, error.message, e)
                    } else {
                        AppException(ErrorEnum.NETWORK_ERROR, e)
                    }
                    return ex
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                    ex = AppException(ErrorEnum.PARSE_ERROR, e)
                    return ex
                }
                is ConnectException -> {
                    ex = AppException(ErrorEnum.NETWORK_ERROR, e)
                    return ex
                }
                is javax.net.ssl.SSLException -> {
                    ex = AppException(ErrorEnum.SSL_ERROR, e)
                    return ex
                }
                is ConnectTimeoutException -> {
                    ex = AppException(ErrorEnum.TIMEOUT_ERROR, e)
                    return ex
                }
                is java.net.SocketTimeoutException -> {
                    ex = AppException(ErrorEnum.TIMEOUT_ERROR, e)
                    return ex
                }
                is java.net.UnknownHostException -> {
                    ex = AppException(ErrorEnum.TIMEOUT_ERROR, e)
                    return ex
                }
                is AppException -> return it

                else -> {
                    ex = AppException(ErrorEnum.UNKNOWN, e)
                    return ex
                }
            }
        }
        ex = AppException(ErrorEnum.UNKNOWN, e)
        return ex
    }

}