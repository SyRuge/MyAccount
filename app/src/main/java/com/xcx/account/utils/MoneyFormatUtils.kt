package com.xcx.account.utils

import java.math.BigDecimal

/**
 * Created by SyRuge on 2020年12月30日.
 */
/**
 * return like 22.35
 */
fun getMoneyWithTwoDecimal(money: Long): String {
    val m = BigDecimal(money.toString()).divide(BigDecimal.valueOf(100.0))
        .setScale(2, BigDecimal.ROUND_DOWN).toString()
    return m
}
