package com.xcx.account.bean

/**
 * Created by SyRuge on 2021年02月03日.
 */
data class PayCountBean(
    var categoryName: String,
    var payMoney: Long,
    var totalMoney: Long,
    var startTime: Long,
    var endTime: Long,
    var color: Int,
    var showCountType: Int,
) : BaseDataBean()