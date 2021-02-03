package com.xcx.account.utils

import android.app.Activity
import android.content.Intent
import com.xcx.account.ui.activity.BaseActivity
import com.xcx.account.ui.activity.PayListActivity
import com.xcx.account.ui.fragment.BaseFragment

/**
 * Created by SyRuge on 2021年02月03日.
 */
fun BaseActivity.startPayListActivity(
    listType: Int,
    startTime: Long,
    endTime: Long,
    payCategory: String = ""
) {
    val intent = Intent(this, PayListActivity::class.java).apply {
        putExtra(PAY_LIST_TYPE_KEY, listType)
        putExtra(PAY_LIST_START_TIME_KEY, startTime)
        putExtra(PAY_LIST_END_TIME_KEY, endTime)
        putExtra(PAY_LIST_CATEGORY_KEY, payCategory)
    }
    startActivity(intent)
}

fun BaseFragment.startPayListActivity(
    listType: Int,
    startTime: Long,
    endTime: Long,
    payCategory: String = ""
) {
    val intent = Intent(context, PayListActivity::class.java).apply {
        putExtra(PAY_LIST_TYPE_KEY, listType)
        putExtra(PAY_LIST_START_TIME_KEY, startTime)
        putExtra(PAY_LIST_END_TIME_KEY, endTime)
        putExtra(PAY_LIST_CATEGORY_KEY, payCategory)
    }
    startActivity(intent)
}