package com.lujianye.mvpdemo.mvp.ui

import com.lujianye.mvpdemo.R
import com.lujianye.mvpdemo.mvp.base.BaseActivity
import com.lujianye.mvpdemo.utils.disableShiftMode
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主页
 */
class MainActivity : BaseActivity() {

    override fun getLayoutResID(): Int = R.layout.activity_main

    override fun initData() {
        disableShiftMode(main_bottom_navigation)
    }
}

