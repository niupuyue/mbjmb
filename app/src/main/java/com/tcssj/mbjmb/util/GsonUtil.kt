package com.tcssj.mbjmb.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Gson操作类
 *
 * @author 邹峰立
 */
object GsonUtil {

    val gson = Gson()

    val gsonSerializeNulls: Gson = GsonBuilder().serializeNulls().create()
    val gsonEscaping: Gson = GsonBuilder().disableHtmlEscaping().create()

    fun isJson(content: String): Boolean {
        return try {
            if (content.contains("[") && content.contains("]")) {
                JSONArray(content)
                true
            } else {
                JSONObject(content)
                true
            }
        } catch (ex: JSONException) {
            false
        }
    }

}