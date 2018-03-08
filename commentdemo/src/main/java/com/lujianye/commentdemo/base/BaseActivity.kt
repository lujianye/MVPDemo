package com.lujianye.commentdemo.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

/**
 * Description : 父类
 * Author : lujianye
 * Date : 2018/3/8
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        super.onCreate(savedInstanceState)

        //设置布局文件
        setContentView(getLayoutResID())
        //初始化
        init()
    }

    /**
     *  设置布局文件
     */
    abstract fun getLayoutResID(): Int

    /**
     * 初始化
     */
    abstract fun init()
}