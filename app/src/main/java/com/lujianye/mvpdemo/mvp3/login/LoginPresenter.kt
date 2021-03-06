package com.lujianye.mvpdemo.mvp3.login

import com.lujianye.mvpdemo.mvp1.LoginModel
import com.lujianye.mvpdemo.mvp3.MvpPresenter
import com.lujianye.mvpdemo.utils.HttpUtils

/**
 * Description : TODO
 * Author : lujianye
 * Date : 2018/2/24
 */
//P层
//两个特点：
//特点一：持有V层的引用
//特点二：持有M层的引用
class LoginPresenter : MvpPresenter<LoginView>() {

    //特点二：持有M层的引用
    private var loginModel: LoginModel = LoginModel()

    //提供一个业务方法
    fun login() {
        this.loginModel.login(HttpUtils.OnHttpResultListener {
            if (getView() != null) {
                getView()?.onLoginResult(it)
            }
        })
    }
}