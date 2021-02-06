package com.xcx.account.utils

import android.util.TypedValue
import com.xcx.account.AccountApp


/**
 * Create By SyRuge at 2021-01-19
 */
fun dp2px(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
        AccountApp.appContext.resources.displayMetrics
    ).toInt()
}

fun sp2px(spValue: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        spValue.toFloat(),
        AccountApp.appContext.resources.displayMetrics
    ).toInt()
}