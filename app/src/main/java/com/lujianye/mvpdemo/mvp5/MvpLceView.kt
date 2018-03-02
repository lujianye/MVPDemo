package com.lujianye.mvpdemo.mvp5

import com.lujianye.mvpdemo.mvp3.MvpView

/**
 * Description : 代理模式 -> 角色一：目标接口
 * Author : lujianye
 * Date : 2018/2/28
 */
interface MvpLceView<D> : MvpView {

    /**
     * 是否是下拉刷新页面
     * 分为两种场景
     * 场景一：LCE设计 -> 提供LCE
     * 场景二：不需要默认LCE加载页面 -> 直接使用下拉刷新组件自带的
     * @param isPullRefresh
     */
    fun showLoading(isPullRefresh: Boolean)

    fun showContent(isPullRefresh: Boolean)

    fun showError(isPullRefresh: Boolean)

    //绑定数据
    fun bindData(data: D, isPullRefresh: Boolean)

    //加载数据
    fun loadData(isPullRefresh: Boolean)
}