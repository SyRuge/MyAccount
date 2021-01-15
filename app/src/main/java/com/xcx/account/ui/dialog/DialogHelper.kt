package com.xcx.account.ui.dialog

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by xuchongxiang on 2021年01月14日.
 */
private var listener: DialogListener? = null
private var dialog: AlertDialog? = null

fun showCommonDialog(activity: Activity, title: String, msg: String, l: DialogListener.() -> Unit) {
    closeDialog()
    listener = DialogListener().also(l)
    dialog = AlertDialog.Builder(activity)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton("确定") { dialog, which ->
            listener?.onConfirm?.invoke()
            dialog.dismiss()
        }.setNegativeButton("取消") { dialog, which ->
            listener?.onCancel?.invoke()
            dialog.dismiss()
        }
        .create()
    dialog?.show()
}

fun closeDialog() {
    dialog?.apply {
        if (!isShowing) {
            dismiss()
        }
    }
    dialog = null
}

class DialogListener {
    var onConfirm: (() -> Unit)? = null
    var onCancel: (() -> Unit)? = null

    fun onConfirm(confirm: () -> Unit) {
        onConfirm = confirm
    }

    fun onCancel(cancel: () -> Unit) {
        onCancel = cancel
    }
}