package com.lujianye.mvpdemo.mvp.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.yokeyword.fragmentation.SupportActivity

/**
 * activity父类
 * Created by cc_013 on 2018/2/8.
 */
abstract class BaseActivity : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //加载布局
        setContentView(getLayoutResID())
        //初始化
        initData()
    }

    //初始化
    abstract fun initData()

    //布局文件
    abstract fun getLayoutResID(): Int
}