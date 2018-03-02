package com.lujianye.mvpdemo.mvp5

import android.view.View
import com.lujianye.mvpdemo.R
import com.lujianye.mvpdemo.mvp3.MvpActivity
import com.lujianye.mvpdemo.mvp3.MvpPresenter
import com.lujianye.mvpdemo.mvp3.MvpView
import com.lujianye.mvpdemo.mvp5.animator.DefaultLceAnimator
import com.lujianye.mvpdemo.mvp5.animator.ILceAnimator
import com.lujianye.mvpdemo.mvp5.impl.MvpLceViewImpl

/**
 * Description : 代理对象
 * 两个特点
 * 特点一：实现目标接口
 * 特点二：持有目标对象引用
 * Author : lujianye
 * Date : 2018/2/28
 */
class MvpLceActivity<D, V : MvpView, P : MvpPresenter<V>> : MvpActivity<V, P>(), MvpLceView<D> {

    //特点二：持有目标对象引用
    var mvpLceView: MvpLceViewImpl<D>? = null
        get() {
            if (field == null) {
                field = MvpLceViewImpl<D>()
            }

            return field
        }

    fun getMvpLceView1(): MvpLceViewImpl<D>? {
        if (mvpLceView == null) {
            this.mvpLceView = MvpLceViewImpl<D>()
        }
        return mvpLceView
    }

    override fun onContentChanged() {
        super.onContentChanged()
        getMvpLceView1()?.initLceView(findViewById(R.id.rootView))
        getMvpLceView1()?.setOnErrorViewClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                onClickError(p0)
            }

        })
    }

    private fun onClickError(view: View?) {
        loadData(false)
    }

    fun setLceAnimator(lceAnimator: ILceAnimator) {
        getMvpLceView1()?.setLceAnimator2(lceAnimator)
    }

    override fun showLoading(isPullRefresh: Boolean) {

        getMvpLceView1()?.showLoading(isPullRefresh)
    }

    override fun showContent(isPullRefresh: Boolean) {
        getMvpLceView1()?.showContent(isPullRefresh)
    }

    override fun showError(isPullRefresh: Boolean) {
        getMvpLceView1()?.showError(isPullRefresh)
    }

    override fun bindData(data: D, isPullRefresh: Boolean) {
        getMvpLceView1()?.bindData(data, isPullRefresh)
    }

    override fun loadData(isPullRefresh: Boolean) {
        getMvpLceView1()?.loadData(isPullRefresh)
    }

}