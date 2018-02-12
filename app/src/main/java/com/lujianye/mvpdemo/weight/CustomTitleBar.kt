package com.lujianye.mvpdemo.weight

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import com.lujianye.mvpdemo.R
import org.jetbrains.anko.*

/**
 * Description : TODO
 * Author : cc_013
 * Date : 2018/2/10
 */
class CustomTitleBar : LinearLayoutCompat {

    private var isLeftBtnVisible: Int = View.GONE
    private var leftResId: Int = -1
    private var isLeftTvVisible: Int = View.GONE
    private var leftTvText: String? = null
    private var isRightBtnVisible: Int = View.GONE
    private var rightResId: Int = -1
    private var isRightTvVisible: Int = View.GONE
    private var rightTvText: String? = null
    private var isTitleVisible: Int = View.GONE
    private var titleText: String? = null
    private var titleLayout: Int = -1
    private var barBackground: Int = -1

    constructor(context: Context) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    /**
     * 初始化属性
     * @param attrs 参数
     */
    private fun initView(attrs: AttributeSet?) {
        //step 1：获取自定义属性
        context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar).apply {
            /**-------------获取左边按钮属性------------*/
            isLeftBtnVisible = getInt(
                    R.styleable.CustomTitleBar_left_btn_visible,
                    View.GONE
            )
            leftResId = getResourceId(
                    R.styleable.CustomTitleBar_left_btn_src,
                    -1
            )
            /**-------------获取左边文本属性------------*/
            isLeftTvVisible = getInt(
                    R.styleable.CustomTitleBar_left_tv_visible,
                    View.GONE
            )
            if (hasValue(R.styleable.CustomTitleBar_left_tv_text)) {
                leftTvText = getString(R.styleable.CustomTitleBar_left_tv_text)
            }
            /**-------------获取右边按钮属性------------*/
            isRightBtnVisible = getInt(
                    R.styleable.CustomTitleBar_right_btn_visible,
                    View.GONE
            )
            rightResId = getResourceId(
                    R.styleable.CustomTitleBar_right_btn_src,
                    -1
            )
            /**-------------获取右边文本属性------------*/
            isRightTvVisible = getInt(
                    R.styleable.CustomTitleBar_right_tv_visible,
                    View.GONE
            )
            if (hasValue(R.styleable.CustomTitleBar_right_tv_text)) {
                rightTvText = getString(R.styleable.CustomTitleBar_right_tv_text)
            }
            /**-------------获取标题属性------------*/
            isTitleVisible = getInt(
                    R.styleable.CustomTitleBar_title_visible,
                    View.GONE
            )
            if (hasValue(R.styleable.CustomTitleBar_title_text)) {
                titleText = getString(R.styleable.CustomTitleBar_title_text)
            }
            titleLayout = getResourceId(
                    R.styleable.CustomTitleBar_title_layout,
                    -1
            )
            /**-------------背景颜色------------*/
            barBackground = getResourceId(R.styleable.CustomTitleBar_barBackground, -1)
        }.recycle()

        val barLayoutView = View.inflate(context, R.layout.view_customtitlebar, null)
    }

}
