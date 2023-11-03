package com.tcssj.mbjmb.net.interceptor

import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.io.IOException
import java.util.*

/**
 * @author:Niu Puyue
 * e-mail:niupuyue@aliyun.com
 * time:2021/12/21 3:02 下午
 * desc: 动态修改BaseUrl 拦截器
 */
class BaseUrlInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //获取request
        val request = chain.request()
        //从request中获取原有的HttpUrl实例oldHttpUrl
        var oldHttpUrl = request.url
        //获取request的创建者builder
        val builder = request.newBuilder()
        //从request中获取headers，通过给定的键url_name
        val headerValues = request.headers("urlName")
        if (headerValues.isNotEmpty()) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader("urlName")
            //匹配获得新的BaseUrl
            val headerValue = headerValues[0]
            var newBaseUrl: HttpUrl = oldHttpUrl
            if ("weichat" == headerValue) {
                newBaseUrl = "https://api.weixin.qq.com/sns/".toHttpUrl()
            }
            val oldBuilder = oldHttpUrl.newBuilder()
            for (i in 0 until oldHttpUrl.pathSize) {
                oldBuilder.removePathSegment(0)
            }
            val newPathSegments = arrayListOf<String>()
            newPathSegments.addAll(newBaseUrl.encodedPathSegments)
            newPathSegments.addAll(oldHttpUrl.encodedPathSegments)

            for (PathSegment in newPathSegments) {
                oldBuilder.addEncodedPathSegment(PathSegment)
            }
            val newFullUrl = oldBuilder
                .scheme(newBaseUrl.scheme) //更换网络协议
                .host(newBaseUrl.host) //更换主机名
                .port(newBaseUrl.port) //更换端口
                .build()
            //重建这个request，通过builder.url(newFullUrl).build()；
            // 然后返回一个response至此结束修改
            Log.e("Url", "intercept: $newFullUrl")
            return chain.proceed(builder.url(newFullUrl).build())
        }
        return chain.proceed(request)
    }

}