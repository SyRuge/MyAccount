package com.xcx.account.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xcx.account.AccountApp
import com.xcx.account.R

/**
 * Created by xuchongxiang on 2020年12月28日.
 */
class MoneyNumberAdapter(var context: Context = AccountApp.appContext) :
    RecyclerView.Adapter<MoneyNumberAdapter.NumberHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.rv_item_number, parent, false)
        return NumberHolder(view)
    }

    override fun getItemCount(): Int {
        return 16
    }

    override fun onBindViewHolder(holder: NumberHolder, position: Int) {
    }

    class NumberHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}