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
 * Created by SyRuge on 2020年12月18日.
 */
class TodayPayAdapter(
    var context: Context = AccountApp.appContext,
    var payList: List<HomePayBean>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listener: ItemClickListener? = null
    private var deleteListener: ItemDeleteListener? = null

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }
    fun setOnItemDeleteListener(listener: ItemDeleteListener) {
        this.deleteListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.rv_item_pay_header, parent, false)
            HeaderHolder(view)
        } else {
            val view =
                LayoutInflater.from(context).inflate(R.layout.rv_item_today_pay, parent, false)
            PayHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderHolder) {
            holder.tvPayHeaderTitle.setText(R.string.today_pay_dec)
        }
        if (holder is PayHolder) {
            val bean = payList[position-1]
            holder.tvPaySellerName.text = bean.paySellerName
            val payMoney = "-¥${getMoneyWithTwoDecimal(bean.payMoney)}"
            holder.tvPayMoney.text = payMoney
            holder.tvPayDate.text = getTime(bean.payTime)
            if (bean.payNote.isNotEmpty()){
                holder.tvPayCategory.text = bean.payNote
            } else {
                holder.tvPayCategory.text = bean.payCategory
            }
            if (position == payList.size) {
                holder.viewPayLine.visibility = View.INVISIBLE
            } else {
                holder.viewPayLine.visibility = View.VISIBLE
            }
            holder.itemView.setOnClickListener {
                listener?.onItemClick(bean)
            }
            holder.itemView.setOnLongClickListener {
                deleteListener?.onItemDelete(bean)
                true
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

    class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvPayHeaderTitle: TextView = itemView.findViewById(R.id.tv_pay_header_title)
    }

    fun interface ItemClickListener {
        fun onItemClick(bean: HomePayBean)
    }
    fun interface ItemDeleteListener {
        fun onItemDelete(bean: HomePayBean)
    }
}