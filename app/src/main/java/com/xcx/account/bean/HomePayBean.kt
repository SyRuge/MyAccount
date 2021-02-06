package com.xcx.account.bean


/**
 * Created by SyRuge on 2020年12月18日.
 */
data class HomePayBean(
    var id: Long,
    var payId: String,
    var paySellerName: String,
    var payMoney: Long,
    var payCategory: String,
    var payTime: Long,
    var payDate: String,
    var payNote: String,
) : BaseDataBean()