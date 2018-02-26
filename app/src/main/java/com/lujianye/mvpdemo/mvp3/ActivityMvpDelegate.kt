package com.lujianye.mvpdemo.mvp3

import android.os.Bundle

/**
 * Description : 第一重代理->目标接口（定义了Activity的生命周期）
 * Author : lujianye
 * Date : 2018/2/26
 */
interface ActivityMvpDelegate<V : MvpView, P : MvpPresenter<V>> {

    fun onCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onRestart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()
}