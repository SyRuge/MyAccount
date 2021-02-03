package com.xcx.account.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xcx.account.AccountApp
import com.xcx.account.R
import com.xcx.account.bean.HomeTotalPayBean
import com.xcx.account.utils.getMoneyWithTwoDecimal

/**
 * Created by SyRuge on 2020年12月18日.
 */
class TotalPayAdapter(
    var context: Context = AccountApp.appContext,
    var payList: MutableList<HomeTotalPayBean>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listener: ItemClickListener? = null

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.rv_item_pay_header, parent, false)
            HeaderHolder(view)
        } else {
            val view =
                LayoutInflater.from(context).inflate(R.layout.rv_item_total_pay, parent, false)
            TotalHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderHolder) {
            holder.tvPayHeaderTitle.setText(R.string.total_pay_dec)
        }
        if (holder is TotalHolder) {
            val bean = payList[position-1]
            holder.tvTotalPayName.text = bean.totalPayName
            val payMoney = "-¥${getMoneyWithTwoDecimal(bean.payMoney)}"
            holder.tvTotalPayMoney.text = payMoney
            holder.tvPayRangeDate.text = bean.payRangeDate
            if (position == payList.size) {
                holder.viewTotalPayLine.visibility = View.INVISIBLE
            } else {
                holder.viewTotalPayLine.visibility = View.VISIBLE
            }
            holder.itemView.setOnClickListener {
                listener?.onItemClick(bean, position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 1
        }
        return 0
    }

    override fun getItemCount(): Int {
        return payList.size + 1
    }

    class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvPayHeaderTitle: TextView = itemView.findViewById(R.id.tv_pay_header_title)
    }

    class TotalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTotalPayName: TextView = itemView.findViewById(R.id.tv_total_pay_name)
        var tvTotalPayMoney: TextView = itemView.findViewById(R.id.tv_total_pay_money)
        var tvPayRangeDate: TextView = itemView.findViewById(R.id.tv_pay_range_date)
        var viewTotalPayLine: View = itemView.findViewById(R.id.view_total_pay_line)
    }
    fun interface ItemClickListener {
        fun onItemClick(bean: HomeTotalPayBean, position: Int)
    }
}