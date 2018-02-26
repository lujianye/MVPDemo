package com.lujianye.mvpdemo.mvp2

import android.app.Activity
import android.os.Bundle


/**
 * Description : TODO
 * Author : lujianye
 * Date : 2018/2/24
 */
//特点一：持有P层引用
//特点二：实现交互接口
abstract class BaseActivity<V : MvpView, out P : BasePresenter<V>> : Activity(), MvpView {

    private var presenter: P? = null

    fun getPresenter(): P? = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (this.presenter == null) {
            this.presenter = createPresenter()
        }
        if (this.presenter != null) {
            //绑定V层
            this.presenter!!.attachView(this as V)
        }
    }

    abstract fun createPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        //解绑
        if (this.presenter != null) {
            this.presenter!!.detachView()
        }
    }
}