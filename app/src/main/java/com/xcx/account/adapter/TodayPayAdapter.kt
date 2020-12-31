package com.xcx.account.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xcx.account.AccountApp
import com.xcx.account.R
import com.xcx.account.bean.HomePayBean
import com.xcx.account.utils.getMoneyWithTwoDecimal
import com.xcx.account.utils.getTime
import java.math.BigDecimal

/**
 * Created by xuchongxiang on 2020年12月18日.
 */
class TodayPayAdapter(
    var context: Context = AccountApp.appContext,
    var payList: List<HomePayBean>
) : RecyclerView.Adapter<TodayPayAdapter.PayHolder>() {

    private var listener: ItemClickListener? = null

    fun setOnitemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.rv_item_today_pay, parent, false)
        return PayHolder(view)
    }

    override fun onBindViewHolder(holder: PayHolder, position: Int) {
        val bean = payList[position]
        holder.tvPaySellerName.text = bean.paySellerName
        val payMoney = "-￥${getMoneyWithTwoDecimal(bean.payMoney)}"
        holder.tvPayMoney.text = payMoney
        holder.tvPayCategory.text = bean.payCategory
        holder.tvPayDate.text = getTime(bean.payTime)
        if (position == payList.size - 1) {
            holder.viewPayLine.visibility = View.INVISIBLE
        } else {
            holder.viewPayLine.visibility = View.VISIBLE
        }
        holder.itemView.setOnClickListener {
            listener?.onItemClick(bean)
        }
    }

    override fun getItemCount(): Int {
        return payList.size
    }

    fun updatePayInfoData(list: List<HomePayBean>) {
        payList = list
        notifyDataSetChanged()
    }

    class PayHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvPaySellerName: TextView = itemView.findViewById(R.id.tv_pay_seller_name)
        var tvPayMoney: TextView = itemView.findViewById(R.id.tv_pay_money)
        var tvPayCategory: TextView = itemView.findViewById(R.id.tv_pay_category)
        var tvPayDate: TextView = itemView.findViewById(R.id.tv_pay_date)
        var viewPayLine: View = itemView.findViewById(R.id.view_pay_line)
    }

    fun interface ItemClickListener {
        fun onItemClick(bean: HomePayBean)
    }
}