package com.lujianye.mvpdemo.mvp5.impl

import android.view.View
import com.lujianye.mvpdemo.R
import com.lujianye.mvpdemo.mvp5.animator.ILceAnimator
import com.lujianye.mvpdemo.mvp5.animator.DefaultLceAnimator
import com.lujianye.mvpdemo.mvp5.MvpLceView

/**
 * Description :
 * 加载View（LoadingView，ContentView，ErrorView）控制显示和隐藏
 * Author : lujianye
 * Date : 2018/2/28
 */
class MvpLceViewImpl<D> : MvpLceView<D> {

    var loadingView: View? = null
    var contentView: View? = null
    var errorView: View? = null
    private var lceAnimator: ILceAnimator? = null
        get() {
            if (field == null) {
                field = DefaultLceAnimator()
            }
//            if (lceAnimator == null) {
//                lceAnimator = DefaultLceAnimator()
//            }
            return field
        }

    /**
     * 初始化视图
     */
    open fun initLceView(v: View) {
        if (loadingView == null) {
            loadingView = v.findViewById(R.id.loadingView)
        }

        if (contentView == null) {
            contentView = v.findViewById(R.id.contentView)
        }

        if (errorView == null) {
            errorView = v.findViewById(R.id.errorView)
        }

        if (loadingView == null) {
            throw KotlinNullPointerException("loadingView is not null!")
        }

        if (contentView == null) {
            throw KotlinNullPointerException("contentView is not null!")
        }

        if (errorView == null) {
            throw KotlinNullPointerException("errorView is not null!")
        }
    }

    fun setOnErrorViewClickListener(onClickListener: View.OnClickListener) {
        if (this.errorView != null) {
            this.errorView!!.setOnClickListener(onClickListener)
        }
    }

    fun getLceAnimator1(): ILceAnimator? {
        if (lceAnimator == null) {
            lceAnimator = DefaultLceAnimator()
        }
        return lceAnimator
    }

    fun setLceAnimator2(lceAnimator: ILceAnimator) {
        this.lceAnimator = lceAnimator
    }

    override fun showLoading(isPullRefresh: Boolean) {
        //注意：记得加判断，因为下拉刷新组件有正在加载头部视图，不需要显示加载过程了
        if (!isPullRefresh) {
            getLceAnimator1()?.showLoadingView(loadingView!!, contentView!!, errorView!!)
        }
    }

    override fun showContent(isPullRefresh: Boolean) {
        getLceAnimator1()?.showContentView(loadingView!!, contentView!!, errorView!!)
    }

    override fun showError(isPullRefresh: Boolean) {
        getLceAnimator1()?.showErrorView(loadingView!!, contentView!!, errorView!!)
    }

    override fun bindData(data: D, isPullRefresh: Boolean) {


    }

    override fun loadData(isPullRefresh: Boolean) {


    }
}