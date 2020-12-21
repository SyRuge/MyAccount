package com.xcx.account.bean


/**
 * Created by xuchongxiang on 2020年12月18日.
 */
data class HomePayBean(
    var payId: String,
    var paySellerName: String,
    var payMoney: Double,
    var payCategory: String,
    var payDate: String
) : BaseDataBean()