package com.lujianye.mvpdemo.mvp.ui.activity

import com.lujianye.mvpdemo.R
import com.lujianye.mvpdemo.mvp.adapter.ViewPagerAdapter
import com.lujianye.mvpdemo.mvp.base.BaseActivity
import com.lujianye.mvpdemo.mvp.base.BaseFragment
import com.lujianye.mvpdemo.mvp.ui.fragment.HomeFragment
import com.lujianye.mvpdemo.mvp.ui.fragment.MallFragment
import com.lujianye.mvpdemo.mvp.ui.fragment.MeFragment
import com.lujianye.mvpdemo.mvp.ui.fragment.TranstionFragment
import com.lujianye.mvpdemo.utils.disableShiftMode
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主页
 */
class MainActivity : BaseActivity() {

    lateinit var vpAdapter: ViewPagerAdapter

    companion object {
        var fragmentList: MutableList<BaseFragment> = mutableListOf()
    }
//    lateinit var fragmentList: MutableList<BaseFragment>

    override fun getLayoutResID(): Int = R.layout.activity_main

    override fun initData() {
        //初始化设置
        initSetting()
        //初始化Fragment
        initFragment()
        //初始化ViewPager
        initViewPager()
        //初始化监听
        initListener()
    }

    /**
     * 初始化设置
     */
    private fun initSetting() {
        //取消位移动画
        disableShiftMode(bnvMenu)
        //设置ViewPager是否可以滑动
        vpContent.setScroll(true)
    }

    private fun initFragment() {
        fragmentList.add(HomeFragment())
        fragmentList.add(MallFragment())
        fragmentList.add(TranstionFragment())
        fragmentList.add(MeFragment())
    }

    private fun initViewPager() {
        vpAdapter = ViewPagerAdapter(supportFragmentManager, fragmentList)
        vpContent.adapter = vpAdapter
    }

    /**
     * 初始化监听
     */
    private fun initListener() {
        //底部标题监听
        bnvMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
            //首页
                R.id.home -> {
                    return@setOnNavigationItemSelectedListener true
                }
            //商城
                R.id.mall -> {
                    return@setOnNavigationItemSelectedListener true
                }
            //翻译
                R.id.translation -> {
                    return@setOnNavigationItemSelectedListener true
                }
            //我
                R.id.me -> {
                    return@setOnNavigationItemSelectedListener true
                }
            //其他情况，可不处理
                else -> {
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
    }
}

