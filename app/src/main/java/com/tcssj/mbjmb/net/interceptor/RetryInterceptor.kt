package com.tcssj.mbjmb.net.interceptor

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author:Niu Puyue
 * e-mail:niupuyue@aliyun.com
 * time:2023/3/2 17:43
 * desc: 设置网络请求失败 重试拦截器
 */
class RetryInterceptor : Interceptor {
    // 设置最大重试次数
    companion object {
        const val MAX_RETRY_TIME = 5
        const val RETRY_TYPE_MAX = "RETRY_TYPE_MAX"
    }

    // 当前网络请求已经重试的次数
    private var curRetryTime = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val headerList = request.headers("retry")
        if (headerList.isNotEmpty()) {
            builder.removeHeader("retry")
            val key = headerList[0].toString()
            if (!TextUtils.isEmpty(key) && key == RETRY_TYPE_MAX) {
                var response = chain.proceed(request)
                while (!response.isSuccessful && curRetryTime < MAX_RETRY_TIME) {
                    curRetryTime++
                    response = chain.proceed(request)
                }
                return response
            }
        }
        return chain.proceed(request)
    }
}