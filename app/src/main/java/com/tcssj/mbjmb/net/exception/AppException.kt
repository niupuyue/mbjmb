package cn.emilife.emiapp.net.exception

import com.tcssj.mbjmb.net.enums.ErrorEnum

/**
 * @author:Niu Puyue
 * e-mail:niupuyue@aliyun.com
 * desc: 自定义错误信息异常类
 */
class AppException : Exception {

    var errorMsg: String //错误消息
    var errCode: Int = 0 //错误码
    var errorLog: String? //错误日志
    var throwable: Throwable? = null

    constructor(errCode: Int, error: String?, errorLog: String? = "", throwable: Throwable? = null) : super(error) {
        this.errCode = errCode
        this.errorMsg = error ?: "Something went wrong, please try again."
        this.errorLog = errorLog ?: this.errorMsg
        this.throwable = throwable
    }


    constructor(errCode: Int )   {
        this.errCode = errCode
        this.errorMsg =  "Something went wrong, please try again."
        this.errorLog =  this.errorMsg
        this.throwable = throwable
    }

    constructor(error: ErrorEnum, e: Throwable?) {
        errCode = error.getKey()
        errorMsg = error.getValue()
        errorLog = e?.message
        throwable = e
    }
}