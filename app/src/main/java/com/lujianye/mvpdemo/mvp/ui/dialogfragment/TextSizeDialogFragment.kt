package com.lujianye.mvpdemo.mvp.ui.dialogfragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.lujianye.mvpdemo.R

/**
 * Description : 字体设置
 * Author : cc_013
 * Date : 2018/2/23
 */
class TextSizeDialogFragment : DialogFragment() {

    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //去掉dialogfragment的标题
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //加载布局
        mView = inflater.inflate(R.layout.dialog_textsize, container, false)
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }
}