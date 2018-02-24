package com.lujianye.mvpdemo.mvp.ui.fragment

import com.lujianye.mvpdemo.R
import com.lujianye.mvpdemo.mvp.base.BaseFragment
import com.lujianye.mvpdemo.mvp.ui.dialogfragment.TextSizeDialogFragment
import kotlinx.android.synthetic.main.fragment_me.*

/**
 * Description : TODO
 * Author : cc_013
 * Date : 2018/2/9
 */
class MeFragment : BaseFragment() {
    override fun getLayoutResID(): Int = R.layout.fragment_me

    override fun initData() {
        initListener()
    }

    private fun initListener() {
        me_btn_textSize.setOnClickListener {
            TextSizeDialogFragment().show(fragmentManager, "MeFragment")
        }
    }
}