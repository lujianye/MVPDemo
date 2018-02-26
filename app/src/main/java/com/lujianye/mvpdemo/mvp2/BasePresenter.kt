package com.lujianye.mvpdemo.mvp2


/**
 * Description : TODO
 * Author : lujianye
 * Date : 2018/2/24
 */
//抽象到父类
open class BasePresenter<V : MvpView> {
    private var view: V? = null

    fun getView(): V? {
        return view
    }

    open fun attachView(view: V) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }


}