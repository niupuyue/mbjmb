package com.tcssj.mbjmb.net

import com.tcssj.mbjmb.net.interceptor.BaseUrlInterceptor
import com.tcssj.mbjmb.net.interceptor.RetryInterceptor
import com.tcssj.mbjmb.net.interceptor.logging.LogInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import com.tcssj.mbjmb.EmiApplication
import com.tcssj.mbjmb.net.interceptor.CacheInterceptor
import com.tcssj.mbjmb.net.interceptor.HeaderInterceptor
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author:Niu Puyue
 * @date: 2023年08月01日13:28:34
 * @Description:
 */
class BaseNetworkApi {

    companion object {
        const val DEFAULT_TIMEOUT = 15L
        val INSTANCE: BaseNetworkApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BaseNetworkApi()
        }
    }

    fun <T> getApi(serviceClass: Class<T>, baseUrl: String = "http://47.101.194.189:10018"): T {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
        return retrofitBuilder.build().create(serviceClass)
    }

    /**
     * 配置http
     */
    private val okHttpClient: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            builder.apply {
                connectionPool(ConnectionPool(5, 55, TimeUnit.SECONDS))
                connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                //设置缓存配置 缓存最大10M
                cache(
                    Cache(
                        File(EmiApplication.INSTANCE.cacheDir, "aixizhi_cache"),
                        10 * 1024 * 1024
                    )
                )
                //添加Cookies自动持久化
                cookieJar(cookieJar)
                // 设置重新请求的次数
                addInterceptor(RetryInterceptor())
                //示例：添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
                addInterceptor(HeaderInterceptor())
                //动态url拦截器
                addInterceptor(BaseUrlInterceptor())
                //添加缓存拦截器 可传入缓存天数，不传默认7天
//                addInterceptor(CacheInterceptor())
                // 日志拦截器
                addInterceptor(LogInterceptor())
            }
            return builder.build()
        }

    private val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(EmiApplication.INSTANCE))
    }

}