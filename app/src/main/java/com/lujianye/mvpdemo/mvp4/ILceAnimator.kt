package com.lujianye.mvpdemo.mvp4

import android.view.View

/**
 * Description : LCE动画接口
 * Author : lujianye
 * Date : 2018/2/27
 */
//策略模式 -> 角色一：策略接口
interface ILceAnimator {

    //3个方法
    //方法一：显示加载动画 -> 加载页面
    fun showLoadingView(loadingView: View, contentView: View, errorView: View)

    //方法二：显示内容页面
    fun showContentView(loadingView: View, contentView: View, errorView: View)

    //方法二：显示内容页面
    fun showErrorView(loadingView: View, contentView: View, errorView: View)
}