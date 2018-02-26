package com.lujianye.mvpdemo.mvp3

/**
 * Description : 第二重代理 -> 目标接口
 * Author : lujianye
 * Date : 2018/2/26
 */
interface MvpCallBack<V : MvpView, P : MvpPresenter<V>> {

    //创建P
    fun createPresenter(): P

    //创建V
    fun createView(): V

    //得到P
    fun getPresenter(): P?

    //设置P
    fun setPresenter(presenter: P)

    //得到V
    fun getMvpView(): V
}