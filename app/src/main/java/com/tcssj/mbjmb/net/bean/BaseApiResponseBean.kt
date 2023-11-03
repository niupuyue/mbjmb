package com.tcssj.mbjmb.net.bean

import androidx.annotation.Keep
import com.tcssj.mbjmb.net.BaseResponse

/**
 * @author:Niu Puyue
 * e-mail:niupuyue@aliyun.com
 * desc:服务器返回数据的基类
 * 1.继承 BaseResponse
 * 2.重写isSucces 方法，编写你的业务需求，根据自己的条件判断数据是否请求成功
 * 3.重写 getResponseCode、getResponseData、getResponseMsg方法，传入你的 code data msg
 */
@Keep
data class BaseApiResponseBean<T>(
    val status: Int, val message: String, val body: T, val error_code: Int? = null, val timestamp:String
) : BaseResponse<T>() {

    override fun isSucces() = status == 0 || status == 200

    override fun getResponseCode(): Int {
        return status
    }

    override fun getResponseData() = body

    override fun getResponseMsg() = message

    override fun getErrorCode(): Int? = error_code

}