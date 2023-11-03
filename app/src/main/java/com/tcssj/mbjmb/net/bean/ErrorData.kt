package com.tcssj.mbjmb.net.bean

/**
 * @author:Niu Puyue
 * e-mail:niupuyue@aliyun.com
 * time:5/31/21 2:14 PM
 * desc: 网络请求 错误实体对象
 */
data class ErrorData(
    var msg: String? = "",
    var code: Int = 0
)
