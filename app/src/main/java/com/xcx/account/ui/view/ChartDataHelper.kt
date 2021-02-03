package com.xcx.account.ui.view

import com.xcx.account.repository.database.table.PayInfoBean

/**
 * Created by SyRuge on 2021年02月03日.
 */
@Deprecated("will remove")
class ChartDataHelper {

    companion object {
        private const val TAG = "ChartDataHelper"

        /**
         * 处理PieChart数据
         */
        fun handlePieChartData(list: MutableList<PayInfoBean>): MutableList<PayInfoBean> {
            val sumList = list.groupBy { it.payCategory }
                .values.map {
                    it.reduce { acc, item ->
                        PayInfoBean(
                            acc.id,
                            acc.payId,
                            acc.paySellerName,
                            acc.payMoney + item.payMoney,
                            acc.payCategory,
                            acc.payTime,
                            acc.payDate,
                            acc.payNote
                        )
                    }
                }.toMutableList()
            return sumList
        }
    }
}