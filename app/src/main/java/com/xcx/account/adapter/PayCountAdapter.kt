package com.xcx.account.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xcx.account.AccountApp
import com.xcx.account.R
import com.xcx.account.bean.PayCountBean
import com.xcx.account.ui.view.MyProgressBar
import com.xcx.account.utils.getMoneyWithTwoDecimal
import com.xcx.account.utils.getNumberWithTwoDecimal

/**
 * Create By Ruge at 2021-02-02
 */
class PayCountAdapter(
    var context: Context = AccountApp.appContext,
    var payList: MutableList<PayCountBean>,
) : RecyclerView.Adapter<PayCountAdapter.CountHolder>() {

    private var listener: ItemClickListener? = null

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.rv_item_pay_count_detail, parent, false)
        return CountHolder(view)
    }

    override fun onBindViewHolder(holder: CountHolder, position: Int) {
        val bean = payList[position]
        val money = "-¥${getMoneyWithTwoDecimal(bean.payMoney)}"
        holder.tvPayCategory.text = bean.categoryName
        holder.tvPayMoney.text = money
        setProgressAndColor(holder, bean, position)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(bean)
        }
    }

    override fun getItemCount(): Int {
        return payList.size
    }

    fun updatePayInfoData(list: MutableList<PayCountBean>) {
        payList = list
        notifyDataSetChanged()
    }

    class CountHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvPayMoney: TextView = itemView.findViewById(R.id.tv_pay_money)
        var tvPayCategory: TextView = itemView.findViewById(R.id.tv_pay_category)
        var pbPayCount: MyProgressBar = itemView.findViewById(R.id.pb_pay_count)
    }

    private fun setProgressAndColor(holder: CountHolder, bean: PayCountBean, position: Int) {

        val roundRadius = 6 // 3dp 圆角半径 The x-radius of the oval used to round the corners
        // 准备progressBar带圆角的背景Drawable
        val progressBg = GradientDrawable()
        //设置圆角弧度
        progressBg.cornerRadius = roundRadius.toFloat()
        //设置绘制颜色
        progressBg.setColor(Color.parseColor("#FFDBDBDB"))
        //准备progressBar带圆角的进度条Drawable
        val progressContent = GradientDrawable()
        progressContent.cornerRadius = roundRadius.toFloat()
        //设置绘制颜色，此处可以自己获取不同的颜色
        progressContent.setColor(bean.color)
        //ClipDrawable是对一个Drawable进行剪切操作，可以控制这个drawable的剪切区域，以及相相对于容器的对齐方式
        val progressClip = ClipDrawable(progressContent, Gravity.START, ClipDrawable.HORIZONTAL)
        //Setup LayerDrawable and assign to progressBar
        // 待设置的Drawable数组
        val progressDrawables = arrayOf(progressBg, progressClip)
        val progressLayerDrawable = LayerDrawable(progressDrawables)
        //根据ID设置progressBar对应内容的Drawable
        progressLayerDrawable.setId(0, android.R.id.background)
        progressLayerDrawable.setId(1, android.R.id.progress)
        //设置progressBarDrawable
        holder.pbPayCount.progressDrawable = progressLayerDrawable
        val percent = bean.payMoney * 100.0f / bean.totalMoney
        val text = "${getNumberWithTwoDecimal(percent)}%"
//        holder.pbPayCount.progress = percent.toInt()
        holder.pbPayCount.setProgressAndPercentText(percent.toInt(), text)
    }

    fun interface ItemClickListener {
        fun onItemClick(bean: PayCountBean)
    }
}