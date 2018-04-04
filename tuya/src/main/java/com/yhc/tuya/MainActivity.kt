package com.yhc.tuya

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.*
import android.hardware.Camera
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Environment.getExternalStorageDirectory
import android.support.v4.app.FragmentActivity
import android.widget.ImageView
import android.widget.Toast
import java.io.*
import java.util.logging.Logger
import java.nio.file.Files.delete
import java.nio.file.Files.exists




class MainActivity : AppCompatActivity(), SurfaceHolder.Callback {

    var mCamera: Camera? = null
    var mHolder: SurfaceHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestCameraPermission()
        verifyStoragePermissions(this)
        mHolder = svContent.holder
        mHolder?.addCallback(this)

        // 监听
        initListener()

        xmode_view.setActivity(this)
        xmode_view.clearCanvas()

        xmode_view2.setActivity(this)
        xmode_view2.clearCanvas()

        xmode_view.setOnTouchCutListener(object : XfermodeView.TouchCutListener {
            override fun onTouch(event: MotionEvent?) {
                xmode_view2.setTouch(event)
            }

            override fun onEvent(event: Int) {


            }

            override fun touchBackUrl(filePath: String?) {

            }

            override fun touchCutError(error: String?) {

            }

        })
        xmode_view2.setOnTouchCutListener(object : XfermodeView2.TouchCutListener {
            override fun onEvent(event: Int) {

            }

            override fun touchBackUrl(filePath: String?) {

            }

            override fun touchCutError(error: String?) {

            }

        })
    }

    private fun initListener() {

        btnPictures.setOnClickListener {
            xmode_view.visibility = View.VISIBLE
            xmode_view2.visibility = View.INVISIBLE

        }

        btnLight.setOnClickListener {
            xmode_view.visibility = View.INVISIBLE
            xmode_view2.visibility = View.VISIBLE
        }

        btnSubmit.setOnClickListener {

            xmode_view.visibility = View.INVISIBLE
            xmode_view2.visibility = View.INVISIBLE
//            xmode_view2.setDrawingCacheEnabled(true)
//            xmode_view2.buildDrawingCache()  //启用DrawingCache并创建位图
//            val bitmap = Bitmap.createBitmap(xmode_view2.getDrawingCache()) //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
//            xmode_view2.setDrawingCacheEnabled(false)  //禁用DrawingCahce否则会影响性能
            // 获取需要截取快照的控件
//            val view = findViewById(R.id.my_view)

//            // 设置控件允许绘制缓存
//            xmode_view2.setDrawingCacheEnabled(true)
//            // 获取控件的绘制缓存（快照）
//            val bitmap = xmode_view2.getDrawingCache()
//            saveImage(this, xmode_view2)
            // 保存截取的快照
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, null)
//            iv.setImageBitmap(bitmap)

            saveBitmap(xmode_view2,"asd")
        }


        btnCamera.setOnClickListener {
            //拍照
            var parameters = mCamera?.parameters

            parameters?.apply {
                pictureFormat = ImageFormat.JPEG
                parameters.setPictureSize(svContent.width, svContent.height)
                parameters.focusMode = Camera.Parameters.FOCUS_MODE_AUTO
            }
            mCamera?.apply {
                autoFocus { success, camera ->
                    if (success) {
                        mCamera?.takePicture(null, null, object : Camera.PictureCallback {
                            override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
                                var bitmap = data?.let {
                                    Bytes2Bimap(it)
                                }
                                xmode_view.visibility = View.VISIBLE
                                xmode_view2.visibility = View.VISIBLE
                                xmode_view.setBitmap(bitmap)
                                xmode_view2.setBitmap(bitmap)
                                mCamera?.stopPreview()
                            }

                        })
                    }
                }
            }
        }

    }

    /**
     * 生成视图的预览
     * @param activity
     * @param v
     * @return  视图生成失败返回null
     * 视图生成成功返回视图的绝对路径
     */
    fun saveImage(activity: Activity, v: View): String? {

        val REQUEST_EXTERNAL_STORAGE = 1
        val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permission = ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this@MainActivity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            )
        }
        var bitmap: Bitmap
        val path = Environment.getExternalStorageDirectory().absolutePath + "preview.png"
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        bitmap = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val location = IntArray(2)
        v.getLocationOnScreen(location)
        try {
            bitmap = Bitmap.createBitmap(bitmap, location[0], location[1], v.width, v.height)
            val fout = FileOutputStream(path)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout)
            return path
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.e("123", "生成预览图片失败：" + e)
        } catch (e: IllegalArgumentException) {
            Log.e("123", "width is <= 0, or height is <= 0")
        } finally {
            // 清理缓存
            view.destroyDrawingCache()
        }
        return null

    }


    /** 保存View为图片的方法  */
    fun saveBitmap(v: View, name: String) {


        val fileName = name + ".png"


        val bm = Bitmap.createBitmap(v.width, v.height,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        v.draw(canvas)
        val TAG = "TIKTOK"
        Log.e(TAG, "保存图片")
        val f = File("/sdcard/DCIM/", fileName)
        if (f.exists()) {
            f.delete()
        }
        try {
            val out = FileOutputStream(f)
            bm.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.flush()
            out.close()
            Log.i(TAG, "已经保存")
        } catch (e: FileNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }


    fun Bytes2Bimap(b: ByteArray): Bitmap? {
        return if (b.size != 0) {
            BitmapFactory.decodeByteArray(b, 0, b.size)
        } else {
            null
        }
    }

    override fun onResume() {
        super.onResume()
        if (mCamera == null) {
            mCamera = getCamera()
            if (mHolder != null) {
                setStartPreview(mCamera!!, mHolder!!)
            }
        }
    }


    override fun onPause() {
        super.onPause()
        if (mCamera != null) {
            mCamera?.setPreviewCallback(null)
            mCamera?.stopPreview()
            releaseCamera()
            mCamera = null
        }

    }

    /**
     * 获取Camera对象
     */
    fun getCamera(): Camera? {
        var camera: Camera? = null

        try {
            camera = Camera.open()
        } catch (e: Exception) {
            camera = null
            e.printStackTrace()
        }

        return camera
    }

    /**
     * 开始预览相机内容
     */
    fun setStartPreview(camera: Camera, holder: SurfaceHolder) {
        try {
            camera.setPreviewDisplay(holder)
            camera.setDisplayOrientation(90)
            camera.startPreview()
        } catch (e: Exception) {
        }
    }

    /**
     * 释放相机资源
     */
    fun releaseCamera() {
        mCamera?.release()
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        mCamera?.stopPreview()
        setStartPreview(mCamera!!, mHolder!!)
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        releaseCamera()
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        if (mCamera == null) {
            mCamera = getCamera()
            if (mHolder != null) {
                setStartPreview(mCamera!!, mHolder!!)
            }
        }
        setStartPreview(mCamera!!, mHolder!!)
    }


    /**
     * 申请相机权限
     */
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.CAMERA),
                123)// 第一次申请，就直接申请
        ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                124)
    }


    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")


    fun verifyStoragePermissions(activity: Activity) {

        try {
            //检测是否有写的权限
            val permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE")
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
