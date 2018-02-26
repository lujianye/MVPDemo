package com.lujianye.mvpdemo.mvp3.login

import com.lujianye.mvpdemo.mvp3.MvpView

/**
 * Description : 交互接口
 * Author : lujianye
 * Date : 2018/2/24
 */
interface LoginView : MvpView {
    fun onLoginResult(result: String)
}