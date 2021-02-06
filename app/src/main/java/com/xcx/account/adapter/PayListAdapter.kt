package com.xcx.account.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xcx.account.AccountApp
import com.xcx.account.R
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.utils.getDateAndTime
import com.xcx.account.utils.getMoneyWithTwoDecimal

/**
 * Created by SyRuge on 2020年12月18日.
 */
class PayListAdapter(
    var context: Context = AccountApp.appContext,
    var payList: MutableList<PayInfoBean>,
) : RecyclerView.Adapter<PayListAdapter.PayHolder>() {

    private var listener: ItemClickListener? = null
    private var deleteListener: ItemDeleteListener? = null

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    fun setOnItemDeleteListener(listener: ItemDeleteListener) {
        this.deleteListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.rv_item_pay_list, parent, false)
        return PayHolder(view)
    }

    override fun onBindViewHolder(holder: PayHolder, position: Int) {
        val bean = payList[position]
        holder.tvPaySellerName.text = bean.paySellerName
        val payMoney = "-¥${getMoneyWithTwoDecimal(bean.payMoney)}"
        holder.tvPayMoney.text = payMoney
        holder.tvPayDate.text = getDateAndTime(bean.payTime)
        if (bean.payNote.isNotEmpty()) {
            holder.tvPayCategory.text = bean.payNote
        } else {
            holder.tvPayCategory.text = bean.payCategory
        }
        if (position == payList.size - 1) {
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

    override fun getItemCount(): Int {
        return payList.size
    }

    fun updatePayInfoData(list: MutableList<PayInfoBean>) {
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
        fun onItemClick(bean: PayInfoBean)
    }

    fun interface ItemDeleteListener {
        fun onItemDelete(bean: PayInfoBean)
    }
}