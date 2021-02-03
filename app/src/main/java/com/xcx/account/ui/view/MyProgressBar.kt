package com.xcx.account.ui.view

import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.ProgressBar
import com.xcx.account.utils.dp2px
import com.xcx.account.utils.sp2px


/**
 * Created by SyRuge on 2021年02月04日.
 */
class MyProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ProgressBar(context, attrs, defStyleAttr) {


    private var percentText = ""
    private val textRect: Rect = Rect()
    private val paint: Paint = Paint().apply {
        isDither = true
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        textAlign = Paint.Align.LEFT
        color = Color.WHITE
        textSize = sp2px(12).toFloat()
        typeface = Typeface.MONOSPACE
    }

    fun setProgressAndPercentText(progress: Int, text: String) {
        percentText = text
        setProgress(progress)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (percentText.isNotEmpty()) {
            paint.getTextBounds(percentText, 0, percentText.length, textRect)
            //        float textX = (getWidth() / 2) - textRect.centerX();
            val textX = dp2px(16).toFloat()
            val textY = height / 2.0f - textRect.centerY()
            canvas!!.drawText(percentText, textX, textY, paint)
        }
    }
}