package com.tcssj.mbjmb.util

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject


object ConverUtil {

    /**
     * 将map数据转换为 普通的 json RequestBody
     * @param map 以前的请求参数
     * @return
     */
    fun convertMapToBody(map: Map<*, *>?): RequestBody {
        return RequestBody.create("text/plain; charset=utf-8".toMediaTypeOrNull(), JSONObject(map).toString())
    }

    /**
     * 将map数据转换为图片，文件类型的  RequestBody
     * @param map 以前的请求参数
     * @return 待测试
     */
    fun convertMapToMediaBody(map: Map<*, *>?): RequestBody {
        return RequestBody.create("multipart/form-data; charset=utf-8".toMediaTypeOrNull(), JSONObject(map).toString())
    }

}