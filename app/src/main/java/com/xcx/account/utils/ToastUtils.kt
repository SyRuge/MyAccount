package com.xcx.account.utils

import android.widget.Toast
import com.xcx.account.AccountApp

/**
 * Created by xuchongxiang on 2020年12月18日.
 */
//private lateinit var toast: Toast
fun showToast(msg: String) {
    /*if (!::toast.isInitialized) {
        toast = Toast.makeText(AccountApp.appContext, msg, Toast.LENGTH_SHORT)
    }
    toast.cancel()
    toast.setText(msg)
    toast.show()*/
    Toast.makeText(AccountApp.appContext, msg, Toast.LENGTH_SHORT).show()
}