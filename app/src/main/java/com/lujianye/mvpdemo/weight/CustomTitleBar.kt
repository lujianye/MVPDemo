package com.lujianye.mvpdemo.weight

import android.content.Context
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import com.lujianye.mvpdemo.R

/**
 * Description : TODO
 * Author : cc_013
 * Date : 2018/2/10
 */
class CustomTitleBar : LinearLayoutCompat {

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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar)

        typedArray.recycle()

        //step 2：
    }


}