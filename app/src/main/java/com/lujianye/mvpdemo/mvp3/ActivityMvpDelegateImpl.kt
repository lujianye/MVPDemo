package com.lujianye.mvpdemo.mvp3

import android.os.Bundle

/**
 * Description : 第一重代理->目标对象
 * Author : lujianye
 * Date : 2018/2/26
 */
class ActivityMvpDelegateImpl<V : MvpView, P : MvpPresenter<V>> : ActivityMvpDelegate<V, P> {

    var proxyMvpCallback: ProxyMvpCallback<V, P>? = null

    constructor(mvpCallBack: MvpCallBack<V, P>) {
        this.proxyMvpCallback = ProxyMvpCallback(mvpCallBack)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.proxyMvpCallback?.createPresenter()
        this.proxyMvpCallback?.createView()
        this.proxyMvpCallback?.attachView()

        //例如：数据缓存（savedInstanceState）
        //例如：View状态缓存（弱引用、软引用）
        //这个代理生命周期：做预留功能处理
    }

    override fun onStart() {
    }

    override fun onRestart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }
}