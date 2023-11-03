package com.tcssj.mbjmb.net.interceptor

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author:Niu Puyue
 * e-mail:niupuyue@aliyun.com
 * time:2021/12/21 2:55 下午
 * desc: 网络请求 缓存拦截器
 */
class CacheInterceptor(var day: Int = 7) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .cacheControl(CacheControl.FORCE_CACHE)
            .build()
        val response = chain.proceed(request)
        val maxStale = 60 * 60 * 24 * day
        response.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
            .build()
        return response
    }
}