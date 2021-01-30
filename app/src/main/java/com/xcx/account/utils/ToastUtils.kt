package com.xcx.account.utils

import android.widget.Toast
import com.xcx.account.AccountApp

/**
 * Created by SyRuge on 2020年12月18日.
 */
fun showToast(msg: String) {
    Toast.makeText(AccountApp.appContext, msg, Toast.LENGTH_SHORT).show()
}