package com.lujianye.mvpdemo.mvp3.login

import com.lujianye.mvpdemo.mvp1.LoginModel
import com.lujianye.mvpdemo.mvp3.MvpPresenter
import com.lujianye.mvpdemo.mvp3.MvpView
import com.lujianye.mvpdemo.utils.HttpUtils

/**
 * Description : TODO
 * Author : lujianye
 * Date : 2018/2/26
 */
class LoginContract {

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

    interface LoginView : MvpView {
        fun onLoginResult(result: String)
    }
}