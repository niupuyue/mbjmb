package com.tcssj.mbjmb.net.interceptor

import android.text.TextUtils
import android.util.Log
import com.tcssj.mbjmb.BaseConstant
import com.tcssj.mbjmb.util.BaseCode
import com.tcssj.mbjmb.util.GsonUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * desc   :
 * version: 1.0
 * @author : Zhan Xuzhao
 * e-mail : 649912323@qq.com
 * time   : 2019-12-05 21:07
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
            .newBuilder()
        val tokenMap = HashMap<String, String>()
        tokenMap["sourceChannel"] = "Orange"
        tokenMap["packageName"] = BaseConstant.PACKAGE_ALIAS
        tokenMap["adid"] = ""
        tokenMap["version"] = "12.0.0"
        tokenMap["uuId"] = ""
        val token = GsonUtil.gson.toJson(tokenMap)

        // 添加header
        builder.addHeader("packageName", BaseConstant.PACKAGE_ALIAS)
        val headerCode = BaseCode.encrypt(token, BaseConstant.REQUEST_HEADER_KEY) ?: ""
        Log.e("TAG", "intercept: $headerCode")
        builder.addHeader(BaseConstant.REQUEST_HEADER_NAME, headerCode.replace("\n", ""))
        val request = builder.build()
        return chain.proceed(request)
    }

}