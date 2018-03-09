package com.lujianye.commentdemo.base

import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.LinearLayout
import cn.dreamtobe.kpswitch.util.KPSwitchConflictUtil
import cn.dreamtobe.kpswitch.util.KeyboardUtil
import com.lujianye.commentdemo.R
import com.lujianye.commentdemo.adapter.EmotionGridViewAdapter
import com.lujianye.commentdemo.adapter.EmotionPagerAdapter
import com.lujianye.commentdemo.adapter.HorizontalRecyclerviewAdapter
import com.lujianye.commentdemo.bean.ImageBean
import com.lujianye.commentdemo.utils.DisplayUtils
import com.lujianye.commentdemo.utils.EmotionUtils
import com.lujianye.commentdemo.utils.GlobalOnItemClickManagerUtils
import kotlinx.android.synthetic.main.activity_comment1.*
import kotlinx.android.synthetic.main.layout_send_message_bar.*
import org.jetbrains.anko.toast
import java.util.ArrayList

abstract class BaseCommentActivity2 : BaseActivity1() {

    override fun getLayoutResID(): Int = R.layout.activity_comment1

    override fun init() {
        initUp(fl_content_up)
        //监听软键盘的开启与关闭
        KeyboardUtil.attach(this@BaseCommentActivity2, panel_root) {
            if (it) {

            } else {
                if (panel_root.visibility == View.GONE && sendMsgLayout.visibility == View.GONE) {
                    ll_bottom_navigation.visibility = View.VISIBLE
                }
            }
        }

        //初始化表情
        initEmotion()

        vp_complate_emotion_layout.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            internal var oldPagerPos = 0
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                ll_point_group.playByStartPointToNext(oldPagerPos, position)
                oldPagerPos = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        val list = ArrayList<ImageBean>()
        val model1 = ImageBean()
        model1.icon = resources.getDrawable(R.drawable.ic_emotion)
        model1.flag = "经典笑脸"
        model1.isSelected = true
        list.add(model1)
        //底部tab
        val horizontalRecyclerviewAdapter = HorizontalRecyclerviewAdapter(this@BaseCommentActivity2, list)
        recyclerview_horizontal.setHasFixedSize(true)//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        recyclerview_horizontal.adapter = horizontalRecyclerviewAdapter
        recyclerview_horizontal.layoutManager = GridLayoutManager(this@BaseCommentActivity2, 1, GridLayoutManager.HORIZONTAL, false)

        //创建全局监听
        val globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance(this@BaseCommentActivity2)

//        if (isBindToBarEditText) {
        //绑定当前Bar的编辑框
        globalOnItemClickManager.attachToEditText(send_edt)

//        } else {
//            // false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
//            globalOnItemClickManager.attachToEditText(contentView as EditText)
//            mEmotionKeyboard.bindToEditText(contentView as EditText)
//        }
        //初始化recyclerview_horizontal监听器
//        KPSwitchConflictUtil.attach(panel_root, send_edt, KPSwitchConflictUtil.SwitchClickListener {
//            if (it) {
//                send_edt.clearFocus()
//            } else {
//                send_edt.requestFocus()
//            }
//        }, KPSwitchConflictUtil.SubPanelAndTrigger(sub_panel_1, plus_iv)
//                , KPSwitchConflictUtil.SubPanelAndTrigger(sub_panel_2, voice_text_switch_iv))

        KPSwitchConflictUtil.attach(panel_root, plus_iv, send_edt) {
            //            if (it) {
//                send_edt.requestFocus()
//            } else {
//                send_edt.requestFocus()
//            }
        }

        //返回
        iv_bottom_navigation_back.setOnClickListener {
            toast("返回按钮被点击了！！！")
        }
        //分享
        iv_bottom_navigation_share.setOnClickListener {
            toast("分享按钮被点击了！！！")
        }
        //收藏
        iv_bottom_navigation_collection.setOnClickListener {
            toast("收藏按钮被点击了！！！")
        }
        //点赞
        iv_bottom_navigation_praise.setOnClickListener {
            toast("点赞按钮被点击了！！！")
        }
        //评论列表
        iv_bottom_navigation_comment_list.setOnClickListener {
            toast("评论列表按钮被点击了！！！")
        }
        //评论
        tv_bottom_navigation_comment.setOnClickListener {
            toast("评论按钮被点击了！！！")
            send_edt.requestFocus()
            KeyboardUtil.showKeyboard(send_edt)
            ll_bottom_navigation.visibility = View.GONE
            sendMsgLayout.visibility = View.VISIBLE
        }

        //处理点击上部区域，隐藏评论面板
        fl_content_up.setOnTouchListener { v, event ->
            if (sendMsgLayout.visibility == View.VISIBLE) {
                KeyboardUtil.hideKeyboard(send_edt)
                if (panel_root.visibility == View.VISIBLE && sendMsgLayout.visibility == View.VISIBLE) {
                    ll_bottom_navigation.visibility = View.VISIBLE
                }
                sendMsgLayout.visibility = View.GONE
                panel_root.visibility = View.GONE
                send_edt.clearFocus()
                return@setOnTouchListener false
            } else {
                return@setOnTouchListener true
            }
        }

    }

    var emotionPagerGvAdapter: EmotionPagerAdapter? = null


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_comment)
//
//
//    }

    abstract fun initUp(fl_content_up: FrameLayout)
    /**
     * 初始化表情面板
     * 思路：获取表情的总数，按每行存放7个表情，动态计算出每个表情所占的宽度大小（包含间距），
     *      而每个表情的高与宽应该是相等的，这里我们约定只存放3行
     *      每个面板最多存放7*3=21个表情，再减去一个删除键，即每个面板包含20个表情
     *      根据表情总数，循环创建多个容量为20的List，存放表情，对于大小不满20进行特殊
     *      处理即可。
     */
    private fun initEmotion() {
        //获取屏幕高度
        val screenWidth = DisplayUtils.getScreenWidthPixels(this@BaseCommentActivity2)
        // item的间距
        val spacing = DisplayUtils.dp2px(this@BaseCommentActivity2, 12f)
        // 动态计算item的宽度和高度
        val itemWidth = (screenWidth - spacing * 8) / 7
        //动态计算gridview的总高度
        val gvHeight = itemWidth * 3 + spacing * 6

        val emotionViews = ArrayList<GridView>()
        var emotionNames: MutableList<String> = ArrayList()

        // 遍历所有的表情的key
        for (emojiName in EmotionUtils.getEmojiMap(EmotionUtils.EMOTION_CLASSIC_TYPE).keys) {
            emotionNames.add(emojiName)
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size == 20) {
                val gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight)
                emotionViews.add(gv)
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = ArrayList()
            }
        }

        // 判断最后是否有不足20个表情的剩余情况
        if (emotionNames.size > 0) {
            val gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight)
            emotionViews.add(gv)
        }

        //初始化指示器
        ll_point_group.initIndicator(emotionViews.size)
        // 将多个GridView添加显示到ViewPager中
        emotionPagerGvAdapter = EmotionPagerAdapter(emotionViews)
        vp_complate_emotion_layout.adapter = emotionPagerGvAdapter
        val params = LinearLayout.LayoutParams(screenWidth, gvHeight)
        vp_complate_emotion_layout.layoutParams = params
    }

    /**
     * 创建显示表情的GridView
     */
    fun createEmotionGridView(emotionNames: List<String>, gvWidth: Int, padding: Int, itemWidth: Int, gvHeight: Int): GridView {
        // 创建GridView
        val gv = GridView(this@BaseCommentActivity2)
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent)
        //设置7列
        gv.numColumns = 7
        gv.setPadding(padding, padding, padding, padding)
        gv.horizontalSpacing = padding
        gv.verticalSpacing = padding * 2
        //设置GridView的宽高
        val params = ViewGroup.LayoutParams(gvWidth, gvHeight)
        gv.layoutParams = params
        // 给GridView设置表情图片
        val adapter = EmotionGridViewAdapter(this@BaseCommentActivity2, emotionNames, itemWidth, EmotionUtils.EMOTION_CLASSIC_TYPE)
        gv.adapter = adapter
        //设置全局点击事件
        gv.onItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this@BaseCommentActivity2).getOnItemClickListener(EmotionUtils.EMOTION_CLASSIC_TYPE)
        return gv
    }

    override fun onBackPressed() {

        if (panel_root.visibility == View.VISIBLE) {
            panel_root.visibility = View.GONE
            return
        }

        if (sendMsgLayout.visibility == View.VISIBLE) {
            sendMsgLayout.visibility = View.GONE
            ll_bottom_navigation.visibility = View.VISIBLE
            return
        }

        super.onBackPressed()
    }
}
