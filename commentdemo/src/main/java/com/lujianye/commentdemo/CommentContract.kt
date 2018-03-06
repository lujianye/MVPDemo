package com.lujianye.commentdemo

/**
 * Description : 评论协议类
 * Author : lujianye
 * Date : 2018/3/6
 */
interface CommentContract {
    //V层
    interface View : BaseView<Presenter> {

    }

    //P层
    interface Presenter : BasePresenter {

    }
}