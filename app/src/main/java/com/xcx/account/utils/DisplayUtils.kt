package com.xcx.account.utils

import android.content.Context
import android.util.TypedValue
import com.xcx.account.AccountApp


/**
 * Create By Ruge at 2021-01-19
 */
fun dp2px(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
        AccountApp.appContext.resources.displayMetrics
    ).toInt()
}