package com.xcx.account.utils

import java.math.BigDecimal

/**
 * Created by SyRuge on 2021年02月04日.
 */
/**
 * return like 22.35
 */
fun getNumberWithTwoDecimal(num: Float): String {
    return BigDecimal(num.toString())
        .setScale(2, BigDecimal.ROUND_DOWN).toString()
}
