package com.tcssj.mbjmb.net.interfaces

import com.tcssj.mbjmb.MainActivity
import com.tcssj.mbjmb.net.bean.BaseApiResponseBean
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


/**
 * App请求基础接口
 */
interface IAppBaseService {

    @Headers("Content-Type:text/plain; charset=utf-8")
    @POST("/auth/v3.1/user/sendVerifiyCode")
    fun sendVerifiyCode(@Body bean: MainActivity.RequestBean): Observable<BaseApiResponseBean<String>>

}