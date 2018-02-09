package com.lujianye.mvpdemo.mvp.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 *
 * Created by cc_013 on 2018/2/9.
 */
class BaseFragmentPagerAdapter : FragmentPagerAdapter {

    private var mutableList: MutableList<Fragment>
    //构造函数
    constructor(fragmentManager: FragmentManager, mutableList: MutableList<Fragment>) : super(fragmentManager){
        this.mutableList = mutableList
    }

    override fun getItem(position: Int): Fragment =
        mutableList[position]

    override fun getCount(): Int =
        mutableList.size
}