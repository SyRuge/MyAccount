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
 * Created by SyRuge on 2020年12月28日.
 */
class PayCategoryAdapter(
    val context: Context = AccountApp.appContext,
    var categoryList: MutableList<PayCategoryBean>,
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
        val bean = categoryList[position]
        holder.tvPayCategory.apply {
            text = bean.payCategory
            if (isSelected != bean.isSelect) {
                isSelected = bean.isSelect
            }

            if (position != itemCount - 1) {
                setTextColor(resources.getColor(bean.color, null))
            } else {
                setTextColor(resources.getColor(R.color.secondaryTextColor, null))
            }
        }
        holder.itemView.setOnClickListener {
            if (position != itemCount - 1) {
                updateSelectStatus(bean, position)
            }
            listener?.onItemClick(bean)
        }
    }

    private fun updateSelectStatus(bean: PayCategoryBean, position: Int) {
        bean.isSelect = true
        bean.color = R.color.pink_color_500
        categoryList[preSelectPosition].isSelect = false
        categoryList[preSelectPosition].color = R.color.primaryTextColor
        preSelectPosition = position
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvPayCategory: TextView = itemView.findViewById(R.id.tv_pay_category)
    }

    fun interface OnItemClickListener {
        fun onItemClick(bean: PayCategoryBean)
    }
}