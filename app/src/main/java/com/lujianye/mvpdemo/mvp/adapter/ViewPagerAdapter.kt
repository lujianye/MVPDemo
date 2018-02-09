package com.lujianye.mvpdemo.mvp.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.lujianye.mvpdemo.mvp.base.BaseFragment

/**
 * Created by cc_013 on 2018/2/9.
 */
class ViewPagerAdapter//构造函数
(fragmentManager: FragmentManager, private var mutableList: MutableList<BaseFragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment =
            mutableList[position]

    override fun getCount(): Int =
            mutableList.size
}