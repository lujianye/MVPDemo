package com.lujianye.mvpdemo.mvp.ui.activity

import android.support.v4.view.ViewPager
import android.view.MenuItem
import com.lujianye.mvpdemo.R
import com.lujianye.mvpdemo.mvp.adapter.ViewPagerAdapter
import com.lujianye.mvpdemo.mvp.base.BaseActivity
import com.lujianye.mvpdemo.mvp.base.BaseFragment
import com.lujianye.mvpdemo.mvp.ui.fragment.MainFragment
import com.lujianye.mvpdemo.mvp.ui.fragment.MallFragment
import com.lujianye.mvpdemo.mvp.ui.fragment.MeFragment
import com.lujianye.mvpdemo.mvp.ui.fragment.TranstionFragment
import com.lujianye.mvpdemo.utils.disableShiftMode
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主页
 */
class MainActivity : BaseActivity(), ViewPager.OnPageChangeListener {
    private val MAIN_FRAGMENT = 0
    private val MALL_FRAGMENT = 1
    private val TRANSTION_FRAGMENT = 2
    private val ME_FRAGMENT = 3

    private lateinit var vpAdapter: ViewPagerAdapter

    private var menuItem: MenuItem? = null

    companion object {

        var fragmentList: MutableList<BaseFragment> = mutableListOf()
    }

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
        with(bnvMenu){
            disableShiftMode(this)

        }
        //Viewpager
        with(vpContent) {
            setScroll(false)//设置ViewPager是否可以滑动,false为不可滑动，true为可滑动
            setSmoothScroll(false)
        }
    }

    private fun initFragment() {
        fragmentList.add(MainFragment())
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
        with(bnvMenu) {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                //首页
                    R.id.home -> {
                        vpContent.currentItem = MAIN_FRAGMENT
                        return@setOnNavigationItemSelectedListener true
                    }
                //商城
                    R.id.mall -> {
                        vpContent.currentItem = MALL_FRAGMENT
                        return@setOnNavigationItemSelectedListener true
                    }
                //翻译
                    R.id.translation -> {
                        vpContent.currentItem = TRANSTION_FRAGMENT
                        return@setOnNavigationItemSelectedListener true
                    }
                //我
                    R.id.me -> {
                        vpContent.currentItem = ME_FRAGMENT
                        return@setOnNavigationItemSelectedListener true
                    }
                //其他情况，可不处理
                    else -> {
                        return@setOnNavigationItemSelectedListener true
                    }
                }
            }
        }

        vpContent.apply {
            addOnPageChangeListener(this@MainActivity)
        }
    }

    //    lateinit var fragmentList: MutableList<BaseFragment>
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        bnvMenu.apply {
            if (menuItem != null) {
                menuItem?.isChecked = false
            } else {
                menu.getItem(0).isChecked = false
            }
            menuItem = menu.getItem(position)
            menuItem?.isChecked = true
        }
    }
}

