package com.lujianye.mvpdemo

import android.app.Application
import me.yokeyword.fragmentation.BuildConfig
import me.yokeyword.fragmentation.Fragmentation

/**
 * Created by cc_013 on 2018/2/9.
 */
class MvpApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initFragmentation()
    }

    private fun initFragmentation() {
        // 建议在Application里初始化
        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install()
    }
}