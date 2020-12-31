package com.xcx.account.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xcx.account.AccountApp
import com.xcx.account.R
import com.xcx.account.bean.PayCategoryBean

/**
 * Created by xuchongxiang on 2020年12月28日.
 */
class PayCategoryAdapter(
    val context: Context = AccountApp.appContext,
    var categoryList: MutableList<PayCategoryBean>
) :
    RecyclerView.Adapter<PayCategoryAdapter.CategoryHolder>() {

    private var listener: OnItemClickListener? = null
    private var preSelectPosition = 0

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.rv_item_pay_category, parent, false)
        return CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.tvPayCategory.text = categoryList[position].payCategory
        var bean = categoryList[position]
        holder.tvPayCategory.apply {
            text = bean.payCategory
            if (isSelected != bean.isSelect) {
                isSelected = bean.isSelect
            }
            if (position != itemCount - 1) {
                setTextColor(resources.getColor(R.color.primaryTextColor, null))
            } else {
                setTextColor(resources.getColor(R.color.secondaryTextColor, null))
            }
        }
        holder.itemView.setOnClickListener {
            if (position != itemCount -1) {
                updateSelectStatus(bean, position)
                listener?.onItemClick(bean)
            }
        }
    }

    private fun updateSelectStatus(bean: PayCategoryBean, position: Int) {
        bean.isSelect = true
        categoryList[preSelectPosition].isSelect = false
        preSelectPosition = position
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvPayCategory: TextView = itemView.findViewById(R.id.tv_pay_category)
    }

    interface OnItemClickListener {
        fun onItemClick(bean: PayCategoryBean)
    }
}