package com.xcx.account.ui.view

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.xcx.account.R
import com.xcx.account.utils.logd
import java.util.*

/**
 * Created by SyRuge on 2021年02月01日.
 */
class LineChartMarkView(context: Context) :
    MarkerView(context, R.layout.markerview_linechart) {

    private val TAG = "LineChartMarkView"
    var curTime = 0L

    var tvMarkView: TextView = findViewById(R.id.tv_line_mark)
    val c = Calendar.getInstance()


    override fun refreshContent(e: Entry, highlight: Highlight) {
        logd(TAG, "$e")
        if (curTime > 0) {
            c.timeInMillis = curTime
        }
        val month = c.get(Calendar.MONTH) + 1
        val text = "${month}月${e.x.toInt() + 1}日 -¥${e.y}"
        tvMarkView.text = text
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        //return super.getOffset()
        return MPPointF(-(width / 2.0f), -height.toFloat())
    }
}