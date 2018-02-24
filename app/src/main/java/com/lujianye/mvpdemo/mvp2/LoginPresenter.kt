package com.lujianye.mvpdemo.mvp2

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
class LoginPresenter {
    //特点一：持有V层的引用
    private var loginView: LoginView? = null

    //特点二：持有M层的引用
    private var loginModel: LoginModel = LoginModel()

    //绑定方法
    //方案一：通过构造方法绑定
    //方案二：通过对象方法绑定
    fun attachView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun detachView() {
        this.loginView = null
    }

    //提供一个业务方法
    fun login() {
        this.loginModel.login(HttpUtils.OnHttpResultListener {
            if (loginView != null) {
                loginView?.onLoginResult(it)
            }
        })
    }
}