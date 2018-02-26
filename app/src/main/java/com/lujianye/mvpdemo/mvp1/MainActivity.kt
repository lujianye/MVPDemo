package com.lujianye.mvpdemo.mvp1

import android.os.Bundle
import com.lujianye.mvpdemo.R
import com.lujianye.mvpdemo.mvp2.BaseActivity
import com.lujianye.mvpdemo.mvp2.LoginPresenter_2
import com.lujianye.mvpdemo.mvp2.LoginView_2
import com.lujianye.mvpdemo.mvp3.MvpActivity
import com.lujianye.mvpdemo.mvp3.login.LoginPresenter
import com.lujianye.mvpdemo.mvp3.login.LoginView
import kotlinx.android.synthetic.main.activity_main2.*
import org.jetbrains.anko.toast


/**
 * Description : TODO
 * Author : lujianye
 * Date : 2018/2/24
 */
//V层（MainActivity）特点
//特点一：持有P层引用
//特点二：实现交互接口
//class MainActivity : AppCompatActivity(), LoginView {
//class MainActivity : BaseActivity<LoginView_2, LoginPresenter_2>(), LoginView_2 {
class MainActivity : MvpActivity<LoginView, LoginPresenter>(), LoginView {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContentView(R.layout.activity_main2)
//
//        initListener()
//    }
//
//    private fun initListener() {
//        tv_login.setOnClickListener {
//            HttpTask.Builder().url("http://www.wanandroid.com/tools/mockapi/2878/123")
//                    .method("GET")
//                    .onHttpResultListener {
//                        toast(it)
//                    }.build().execute()
//
//        }
//    }

//    lateinit var loginPresenter: LoginPresenter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContentView(R.layout.activity_main2)
//        this.loginPresenter = LoginPresenter()
//        this.loginPresenter.attachView(this)
//        initListener()
//    }
//
//    private fun initListener() {
//        tv_login.setOnClickListener {
//            this.loginPresenter.login()
//        }
//    }
//
//    override fun onLoginResult(result: String) {
//        //网络回调成功
//        toast(result)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        this.loginPresenter.detachView()
//    }

    //3
//    lateinit var loginPresenter: LoginPresenter_2
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContentView(R.layout.activity_main2)
//        this.loginPresenter = LoginPresenter_2()
//        this.loginPresenter.attachView(this)
//        initListener()
//    }
//
//    private fun initListener() {
//        tv_login.setOnClickListener {
//            this.loginPresenter.login()
//        }
//    }
//
//    override fun onLoginResult(result: String) {
//        //网络回调成功
//        toast(result)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        this.loginPresenter.detachView()
//    }

    //4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initListener()
    }

    private fun initListener() {
        tv_login.setOnClickListener {
            getPresenter()?.login()
        }
    }

    override fun createPresenter(): LoginPresenter = LoginPresenter()

    override fun onLoginResult(result: String) {
        toast(result)
    }
}