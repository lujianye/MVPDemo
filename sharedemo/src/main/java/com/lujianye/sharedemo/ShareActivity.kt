package com.lujianye.sharedemo

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import com.umeng.socialize.utils.Log
import kotlinx.android.synthetic.main.activity_share.*
import java.lang.ref.WeakReference


class ShareActivity : AppCompatActivity() {

    var mShareAction: ShareAction? = null
    var mShareListener: CustomShareListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

//        if (Build.VERSION.SDK_INT >= 23) {
//            val mPermissionList = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS)
//            ActivityCompat.requestPermissions(this, mPermissionList, 123)
//        }
        mShareListener = CustomShareListener(this)
        mShareAction = ShareAction(this@ShareActivity).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.VKONTAKTE,
                SHARE_MEDIA.ALIPAY, SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN,
                SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL, SHARE_MEDIA.YNOTE,
                SHARE_MEDIA.EVERNOTE, SHARE_MEDIA.LAIWANG, SHARE_MEDIA.LAIWANG_DYNAMIC,
                SHARE_MEDIA.LINKEDIN, SHARE_MEDIA.YIXIN, SHARE_MEDIA.YIXIN_CIRCLE,
                SHARE_MEDIA.TENCENT, SHARE_MEDIA.FACEBOOK, SHARE_MEDIA.TWITTER,
                SHARE_MEDIA.WHATSAPP, SHARE_MEDIA.GOOGLEPLUS, SHARE_MEDIA.LINE,
                SHARE_MEDIA.INSTAGRAM, SHARE_MEDIA.KAKAO, SHARE_MEDIA.PINTEREST,
                SHARE_MEDIA.POCKET, SHARE_MEDIA.TUMBLR, SHARE_MEDIA.FLICKR,
                SHARE_MEDIA.FOURSQUARE, SHARE_MEDIA.MORE)
                .addButton("复制文本", "复制文本", "umeng_socialize_copy", "umeng_socialize_copy")
                .addButton("复制链接", "复制链接", "umeng_socialize_copyurl", "umeng_socialize_copyurl")
                .setShareboardclickCallback { snsPlatform, share_media ->
                    if (snsPlatform.mShowWord == "复制文本") {
                        Toast.makeText(this@ShareActivity, "复制文本按钮", Toast.LENGTH_LONG).show()
                    } else if (snsPlatform.mShowWord == "复制链接") {
                        Toast.makeText(this@ShareActivity, "复制链接按钮", Toast.LENGTH_LONG).show()

                    } else {
                        val web = UMWeb(Defaultcontent.url)
                        web.title = "来自分享面板标题"
                        web.description = "来自分享面板内容"
                        web.setThumb(UMImage(this@ShareActivity, R.drawable.logo))
                        ShareAction(this@ShareActivity).withMedia(web)
                                .setPlatform(share_media)
                                .setCallback(mShareListener)
                                .share()
                    }
                };

        umeng_menu_bottom.setOnClickListener {
            mShareAction?.open()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)


    }

    class CustomShareListener(activity: ShareActivity) : UMShareListener {

        private val mActivity: WeakReference<ShareActivity>

        init {
            mActivity = WeakReference(activity)
        }

        override fun onStart(platform: SHARE_MEDIA) {

        }

        override fun onResult(platform: SHARE_MEDIA) {

            if (platform.name == "WEIXIN_FAVORITE") {
                Toast.makeText(mActivity.get(), platform.toString() + " 收藏成功啦", Toast.LENGTH_SHORT).show()
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get(), platform.toString() + " 分享成功啦", Toast.LENGTH_SHORT).show()
                }

            }
        }

        override fun onError(platform: SHARE_MEDIA, t: Throwable?) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), platform.toString() + " 分享失败啦", Toast.LENGTH_SHORT).show()
                if (t != null) {
                    Log.d("throw", "throw:" + t.message)
                }
            }

        }

        override fun onCancel(platform: SHARE_MEDIA) {

            Toast.makeText(mActivity.get(), platform.toString() + " 分享取消了", Toast.LENGTH_SHORT).show()
        }
    }
}
