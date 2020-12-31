package com.xcx.account.bean


/**
 * Created by xuchongxiang on 2020年12月18日.
 */
data class HomeTotalPayBean(
    var totalPayName: String,
    var payRangeDate: String,
    var payMoney: Long,
    var payId: String,
    var payCategory: String
) : BaseDataBean()