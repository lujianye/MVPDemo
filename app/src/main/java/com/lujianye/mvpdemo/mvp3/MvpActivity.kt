package com.lujianye.mvpdemo.mvp3

import android.app.Activity
import android.os.Bundle


/**
 * Description :
 *      第一重代理 -> 代理对象
 *          代理模式有2个特点：特点一：持有目标对象引用；特点二：实现目标接口
 *      第二重代理 -> 目标对象
 * Author : lujianye
 * Date : 2018/2/24
 */
//特点一：持有P层引用
//特点二：实现交互接口
abstract class MvpActivity<V : MvpView, P : MvpPresenter<V>> : Activity(), MvpView, MvpCallBack<V, P> {

    //特点一：持有目标对象引用

//    private var mvpDelegate: ActivityMvpDelegateImpl<V, P>? = null
//
//    fun getMvpDelegate(): ActivityMvpDelegateImpl<V, P>? {
//
//        if (mvpDelegate == null) {
//            this.mvpDelegate = ActivityMvpDelegateImpl<V, P>()
//        }
//        return mvpDelegate
//    }

    private var mvpDelegate: ActivityMvpDelegateImpl<V, P>? = null
        get() {
            if (field == null) {
                this.mvpDelegate = ActivityMvpDelegateImpl(this)
            }
            return field
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpDelegate?.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mvpDelegate?.onStart()
    }

    override fun onRestart() {
        super.onRestart()
        mvpDelegate?.onRestart()
    }

    override fun onResume() {
        super.onResume()
        mvpDelegate?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mvpDelegate?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate?.onDestroy()
    }

    private var presenter: P? = null
    //第二重代理
    override fun createPresenter(): P {
        return presenter!!
    }

    override fun createView(): V {
        return this as V
    }

    override fun getPresenter(): P? {
        return this.presenter
    }

    override fun setPresenter(presenter: P) {
        this.presenter = presenter
    }

    override fun getMvpView(): V {
        return this as V
    }
}