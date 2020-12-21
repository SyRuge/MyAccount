package com.xcx.account.bean


/**
 * Created by xuchongxiang on 2020年12月18日.
 */
data class HomeTotalPayBean(
    var payId: String,
    var totalPayName: String,
    var payMoney: Double,
    var payCategory: String,
    var payRangeDate: String
) : BaseDataBean()