package com.lujianye.mvpdemo.mvp1

import com.lujianye.mvpdemo.utils.HttpTask
import com.lujianye.mvpdemo.utils.HttpUtils

/**
 * Description : TODO
 * Author : lujianye
 * Date : 2018/2/24
 */
//M层->数据层：包括网络、数据库、文件操作等
class LoginModel {
    fun login(onHttpResultListener: HttpUtils.OnHttpResultListener) {
        HttpTask.Builder().url("http://www.wanandroid.com/tools/mockapi/2878/123")
                .method("GET")
                .onHttpResultListener {
                    onHttpResultListener.onResult(it)
                }.build().execute()
    }
}