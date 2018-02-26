package com.lujianye.mvpdemo.mvp3

import kotlinx.coroutines.experimental.newCoroutineContext

/**
 * Description :
 *      第二重代理->代理对象
 * Author : lujianye
 * Date : 2018/2/26
 */
class ProxyMvpCallback<V : MvpView, P : MvpPresenter<V>> : MvpCallBack<V, P> {

    //代理对象：持有目标对象引用 -> MvpActivity
    var mvpCallBack: MvpCallBack<V, P>? = null

    constructor(mvpCallBack: MvpCallBack<V, P>) {
        this.mvpCallBack = mvpCallBack
    }

    override fun createPresenter(): P {
        var presenter = mvpCallBack?.getPresenter()

        if (presenter == null) {
            presenter = mvpCallBack?.createPresenter()
        }

        if (presenter == null) {
            throw NullPointerException("presenter不能够为空！")
        }

        mvpCallBack?.setPresenter(presenter)

        return presenter
    }

    override fun createView(): V {
        var view = mvpCallBack?.createView()

        if (view == null) {
            view = mvpCallBack?.createView()
        }

        if (view == null) {
            throw NullPointerException("view不能够为空！")
        }

        return view
    }

    override fun getPresenter(): P? {
        return mvpCallBack!!.getPresenter()
    }

    override fun setPresenter(presenter: P) {
        mvpCallBack!!.setPresenter(presenter)
    }

    override fun getMvpView(): V {
        return mvpCallBack!!.getMvpView()
    }

     fun attachView() {
        this.getPresenter()?.attachView(getMvpView())
    }

    fun detachView() {
        this.getPresenter()?.detachView()
    }
}