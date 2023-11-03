package com.tcssj.mbjmb.net.enums

enum class ErrorEnum(private val code: Int, private val err: String) {

    /**
     * 未知错误
     */
    UNKNOWN(1000, "Something went wrong, please try again."),

    /**
     * 解析错误
     */
    PARSE_ERROR(1001, "Something went wrong, please try again."),

    /**
     * 网络错误
     */
    NETWORK_ERROR(1002, "Network exception, please try again."),

    /**
     * 证书出错
     */
    SSL_ERROR(1004, "Network exception, please try again."),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006, "Internet connection is slow, please try again later.");

    fun getValue(): String {
        return err
    }

    fun getKey(): Int {
        return code
    }

}