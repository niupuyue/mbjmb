package com.tcssj.mbjmb.net.bean

/**
 * @author:Niu Puyue
 * e-mail:niupuyue@aliyun.com
 * time:2021/12/21 4:49 下午
 * desc: 所有返回实体对象的抽象集合
 */
open class BaseResponseBean(
    //请求是否成功
    var isSuccess: Boolean = true,
    //请求失败的错误信息
    var errorMsg: String = "",
    var other: Any? = null,
    var other2: Any? = null,
    var errorCode: Int? = null,
)