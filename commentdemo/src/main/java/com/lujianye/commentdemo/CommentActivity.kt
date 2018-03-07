package com.lujianye.commentdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.view.ViewGroup
import android.widget.GridView
import android.widget.LinearLayout
import cn.dreamtobe.kpswitch.util.KPSwitchConflictUtil
import cn.dreamtobe.kpswitch.util.KeyboardUtil
import com.lujianye.commentdemo.adapter.EmotionGridViewAdapter
import com.lujianye.commentdemo.adapter.EmotionPagerAdapter
import com.lujianye.commentdemo.adapter.HorizontalRecyclerviewAdapter
import com.lujianye.commentdemo.model.ImageModel
import com.lujianye.commentdemo.utils.DisplayUtils
import com.lujianye.commentdemo.utils.EmotionUtils
import com.lujianye.commentdemo.utils.GlobalOnItemClickManagerUtils
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.layout_send_message_bar.*
import org.jetbrains.anko.toast
import java.util.ArrayList

class CommentActivity : AppCompatActivity() {

    var emotionPagerGvAdapter: EmotionPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        //监听软键盘的开启与关闭
        KeyboardUtil.attach(this@CommentActivity, panel_root) { isShowing -> toast("Keyboard is $isShowing") }

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
        val list = ArrayList<ImageModel>()
        val model1 = ImageModel()
        model1.icon = resources.getDrawable(R.drawable.ic_emotion)
        model1.flag = "经典笑脸"
        model1.isSelected = true
        list.add(model1)
        //底部tab
        val horizontalRecyclerviewAdapter = HorizontalRecyclerviewAdapter(this@CommentActivity, list)
        recyclerview_horizontal.setHasFixedSize(true)//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        recyclerview_horizontal.adapter = horizontalRecyclerviewAdapter
        recyclerview_horizontal.layoutManager = GridLayoutManager(this@CommentActivity, 1, GridLayoutManager.HORIZONTAL, false)

        //创建全局监听
        val globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance(this@CommentActivity)

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
//                send_edt.clearFocus()
//            } else {
//                send_edt.requestFocus()
//            }
        }

    }

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
        val screenWidth = DisplayUtils.getScreenWidthPixels(this@CommentActivity)
        // item的间距
        val spacing = DisplayUtils.dp2px(this@CommentActivity, 12f)
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
        val gv = GridView(this@CommentActivity)
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
        val adapter = EmotionGridViewAdapter(this@CommentActivity, emotionNames, itemWidth, EmotionUtils.EMOTION_CLASSIC_TYPE)
        gv.adapter = adapter
        //设置全局点击事件
        gv.onItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this@CommentActivity).getOnItemClickListener(EmotionUtils.EMOTION_CLASSIC_TYPE)
        return gv
    }
}
