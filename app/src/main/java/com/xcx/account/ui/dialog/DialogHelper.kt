package com.xcx.account.ui.dialog

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.xcx.account.AccountApp
import com.xcx.account.R

/**
 * Created by SyRuge on 2021年01月14日.
 */
private var listener: DialogListener? = null
private var dialog: AlertDialog? = null

fun showCommonDialog(activity: Activity, title: String, msg: String, l: DialogListener.() -> Unit) {
    closeDialog()
    listener = DialogListener().also(l)
    dialog = AlertDialog.Builder(activity)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(AccountApp.appContext.getString(R.string.dialog_confirm)) { dialog, _ ->
            listener?.onConfirm?.invoke()
            dialog.dismiss()
        }.setNegativeButton(AccountApp.appContext.getString(R.string.dialog_cancel)) { dialog, _ ->
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