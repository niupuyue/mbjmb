package com.tcssj.mbjmb

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tcssj.mbjmb.net.BaseObserver
import com.tcssj.mbjmb.net.baseApiService
import com.tcssj.mbjmb.net.bean.BaseApiResponseBean
import com.tcssj.mbjmb.ui.theme.MbjmbTheme
import com.tcssj.mbjmb.util.BaseCode
import com.tcssj.mbjmb.util.GsonUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : ComponentActivity() {

    private lateinit var btn: Button
    private lateinit var content: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.ask)
        content = findViewById(R.id.content)
        btn.setOnClickListener {
            request()
        }
    }

    fun request() {
        val observer = object : Observer<BaseApiResponseBean<String>> {
            override fun onSubscribe(d: Disposable?) {
            }

            override fun onNext(value: BaseApiResponseBean<String>?) {
                Log.e("TAG", "onNext: $value")
//                val result = BaseCode.decrypt(value, BaseCode.base64ToDecode(BaseConstant.REQUEST_KEY))
//                content.text = result
            }

            override fun onError(e: Throwable?) {
                Log.e("TAG", "onError: ", e)
            }

            override fun onComplete() {
            }
        }
        // { "type": "text",  "mobile": "81991419936"}
        val requestJson = GsonUtil.gson.toJson(RequestBaseBean("text", "81991419936"))
        Log.e("TAG", "request: $requestJson")
        val requestBean = RequestBean()
        val requestValue = BaseCode.encrypt(requestJson, BaseCode.base64ToDecode(BaseConstant.REQUEST_KEY)) ?: ""
        requestBean.BFBPY = requestValue
        Log.e("TAG", "request: ${BaseCode.decrypt(requestValue, BaseCode.base64ToDecode(BaseConstant.REQUEST_KEY))}")
        baseApiService.sendVerifiyCode(requestBean).subscribeOn(Schedulers.io()).subscribe(observer)
    }

    data class RequestBean(
        var BFBPY: String = ""
    )

    data class RequestBaseBean(
        var type: String = "",
        var mobile: String = ""
    )
}
