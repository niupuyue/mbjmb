package com.tcssj.mbjmb.util

import android.util.Base64
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * @author:Niu Puyue
 * @date:
 * @Description:
 */
object BaseCode {

    fun base64ToDecode(str: String): ByteArray {
        return Base64.decode(str, Base64.DEFAULT)
    }

    // 解密
    fun encode(key: ByteArray?): String? {
        return Base64.encodeToString(key, Base64.DEFAULT)
    }

    // 加密
    fun encrypt(content: String?, key: String): String? {
        return encrypt(content!!, key.toByteArray())
    }

    // 加密
    fun encrypt(content: String, key: ByteArray?): String? {
        return try {
            //构造密钥
            val skey = SecretKeySpec(key, "utf-8")
            //创建初始向量iv用于指定密钥偏移量(可自行指定但必须为128位)，因为AES是分组加密，下一组的iv就用上一组加密的密文来充当
            val iv = IvParameterSpec(key, 0, 16)
            //创建AES加密器
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val byteContent = content.toByteArray(StandardCharsets.UTF_8)
            //使用加密器的加密模式
            cipher.init(Cipher.ENCRYPT_MODE, skey, iv)
            // 加密
            val result = cipher.doFinal(byteContent)
            //使用BASE64对加密后的二进制数组进行编码
            encode(result)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            content
        }
    }

    // 解密
    fun decrypt(content: String?, key: ByteArray?): String? {
        return try {
            val skey = SecretKeySpec(key, "utf-8")
            val iv = IvParameterSpec(key, 0, 16)
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            //解密时使用加密器的解密模式
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, skey, iv)
            val result = cipher.doFinal(base64ToDecode(content!!))
            // 解密
            String(result)
        } catch (e: Exception) {
            content
        }
    }

}