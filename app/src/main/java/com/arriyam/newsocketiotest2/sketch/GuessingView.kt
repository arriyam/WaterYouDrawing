package com.arriyam.newsocketiotest2.sketch

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.arriyam.newsocketiotest2.dataclass.ObjectConverter
import com.arriyam.newsocketiotest2.dataclass.PaintCoordinates
import com.arriyam.newsocketiotest2.socket.SocketHandler

class GuessingView(context: Context, attrs: AttributeSet?) :
    View(context, attrs) {
    lateinit var width:Integer
    lateinit var height:Integer
    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas
    private lateinit var mPath: Path
    private lateinit var mPaint: Paint
    private var mX = 0f
    private var mY = 0f
    var px= 0f
    var py= 0f

    companion object {
        private const val TOLERANCE = 5f
    }

    init {
        mPath = Path()
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = Color.BLUE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth = 7f
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(mPath, mPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
    }

    private fun startTouch(x: Float, y: Float) {
        mPath.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun moveTouch(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    fun mouseAndroidStart(mouse:String) {
        val mouseAndroid= ObjectConverter.convertPaintCoordinates(mouse)
        startTouch(mouseAndroid.x, mouseAndroid.y)
        invalidate()
    }
    fun mouseAndroidMiddle(mouse:String) {
        val mouseAndroid= ObjectConverter.convertPaintCoordinates(mouse)
        moveTouch(mouseAndroid.x, mouseAndroid.y)
        invalidate()
    }
    fun clearCanvas() {
        mPath.reset()
        invalidate()
    }
}

