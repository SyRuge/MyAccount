package com.xcx.account.utils

import android.util.Log

/**
 * Created by SyRuge on 2020年12月17日.
 */
fun logv(tag: String, msg: String) {
    Log.v(tag, msg)
}
fun logd(tag: String, msg: String) {
    Log.d(tag, msg)
}
fun logi(tag: String, msg: String) {
    Log.i(tag, msg)
}
fun logw(tag: String, msg: String) {
    Log.w(tag, msg)
}
fun loge(tag: String, msg: String) {
    Log.e(tag, msg)
}
fun loge(tag: String, msg: String, tr: Throwable) {
    Log.e(tag, msg, tr)
}