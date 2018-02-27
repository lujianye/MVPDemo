package com.lujianye.mvpdemo.mvp4

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AnimationSet
import androidx.animation.addListener
import androidx.animation.doOnEnd
import androidx.animation.doOnStart
import com.lujianye.mvpdemo.R

/**
 * Description : 策略模式 -> 具体策略
 * Author : lujianye
 * Date : 2018/2/27
 */
class DefaultLceAnimator : ILceAnimator {
    override fun showLoadingView(loadingView: View, contentView: View, errorView: View) {
        contentView.visibility = View.GONE
        errorView.visibility = View.GONE
        loadingView.visibility = View.VISIBLE

    }

    override fun showContentView(loadingView: View, contentView: View, errorView: View) =
            if (contentView.visibility == View.VISIBLE) {
                errorView.visibility = View.GONE
                loadingView.visibility = View.GONE
            } else {
                errorView.visibility = View.GONE

                val resources = loadingView.resources
                var translateInPixels = resources.getDimensionPixelSize(R.dimen.lce_content_view_animation_translate_y)

                val set = AnimatorSet()
                val contentFadeIn = ObjectAnimator.ofFloat(contentView, "alpha", 0f, 1f)
                val contentTranslateIn = ObjectAnimator.ofFloat(contentView, "translationY", translateInPixels.toFloat(), 0f)

                val loadingFadeIn = ObjectAnimator.ofFloat(loadingView, "alpha", 1f, 0f)
                val loadingTranslateIn = ObjectAnimator.ofFloat(loadingView, "translationY", 0f, -translateInPixels.toFloat())

                set.playTogether(contentFadeIn, contentTranslateIn, loadingFadeIn, loadingTranslateIn)

                set.duration = resources.getInteger(R.integer.lce_content_view_show_animation_time).toLong()

                set.addListener {
                    it.doOnStart {
                        contentView.translationY = 0f
                        loadingView.translationY = 0f
                        contentView.visibility = View.VISIBLE
                    }

                    it.doOnEnd {
                        loadingView.visibility = View.GONE
                        loadingView.alpha = 1f
                        contentView.translationY = 0f
                        loadingView.translationY = 0f
                    }
                }

                set.start()
            }

    override fun showErrorView(loadingView: View, contentView: View, errorView: View) {
        contentView.visibility = View.GONE

        val resources = loadingView.resources
        val set = AnimatorSet()
        val errorIn = ObjectAnimator.ofFloat(errorView, "alpha", 1f)
        val loadingOut = ObjectAnimator.ofFloat(loadingView, "alpha", 0f)
        set.playTogether(errorIn, loadingOut)
//        set.duration = @android:integer
        set.duration = resources.getInteger(R.integer.lce_error_view_show_animation_time).toLong()

        set.addListener {
            it.doOnStart {
                errorView.visibility = View.VISIBLE
            }

            it.doOnEnd {
                loadingView.visibility = View.GONE
                loadingView.alpha = 1f//For future showLoading calls
            }
        }

        set.start()
    }
}