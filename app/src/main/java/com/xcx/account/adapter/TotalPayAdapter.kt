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
import com.xcx.account.bean.HomeTotalPayBean
import com.xcx.account.utils.getMoneyWithTwoDecimal
import com.xcx.account.utils.logd
import java.math.BigDecimal

/**
 * Created by xuchongxiang on 2020年12月18日.
 */
class TotalPayAdapter(
    var context: Context = AccountApp.appContext,
    var payList: MutableList<HomeTotalPayBean>
) : RecyclerView.Adapter<TotalPayAdapter.TotalHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.rv_item_total_pay, parent, false)
        return TotalHolder(view)
    }

    override fun onBindViewHolder(holder: TotalHolder, position: Int) {
        val bean = payList[position]
        holder.tvTotalPayName.text = bean.totalPayName
        val payMoney = "-￥${getMoneyWithTwoDecimal(bean.payMoney)}"
        holder.tvTotalPayMoney.text = payMoney
        holder.tvPayRangeDate.text = bean.payRangeDate
        if (position == payList.size - 1) {
            holder.viewTotalPayLine.visibility = View.INVISIBLE
        } else {
            holder.viewTotalPayLine.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return payList.size
    }

    class TotalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTotalPayName: TextView = itemView.findViewById(R.id.tv_total_pay_name)
        var tvTotalPayMoney: TextView = itemView.findViewById(R.id.tv_total_pay_money)
        var tvPayRangeDate: TextView = itemView.findViewById(R.id.tv_pay_range_date)
        var viewTotalPayLine: View = itemView.findViewById(R.id.view_total_pay_line)
    }
}