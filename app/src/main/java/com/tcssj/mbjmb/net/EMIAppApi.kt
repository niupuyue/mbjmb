package com.tcssj.mbjmb.net

import com.tcssj.mbjmb.net.interfaces.IAppBaseService

/**
 * @author:Niu Puyue
 * @date: 2023年08月01日14:03:10
 * @Description: 网络请求接口
 */

val baseApiService: IAppBaseService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    BaseNetworkApi.INSTANCE.getApi(IAppBaseService::class.java)
}