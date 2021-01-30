package com.xcx.account.utils

import android.net.Uri
import com.xcx.account.AccountApp
import java.io.InputStreamReader

/**
 * Created by SyRuge on 2021年01月25日.
 */
private const val TAG = "FileUtils"

fun writeFileByResolver(uri: Uri, arr: ByteArray): Boolean {
    return try {
        val out = AccountApp.appContext.contentResolver.openOutputStream(uri)
        out?.use {
            it.write(arr)
            it.flush()
        }
        true

    } catch (e: Exception) {
        loge(TAG, "writeFileByResolver: ", e)
        false
    }
}

fun readFileByResolver(uri: Uri): String {
    return try {
        val input = AccountApp.appContext.contentResolver.openInputStream(uri)
        val json = input.use {
            InputStreamReader(it).use { isr ->
                isr.readText()
            }
        }
        json

    } catch (e: Exception) {
        loge(TAG, "readFileByResolver: ", e)
        ""
    }
}