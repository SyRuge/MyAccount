package com.xcx.account.ui.activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by xuchongxiang on 2021年01月15日.
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getContentViewByLayoutId() > 0) {
            setContentView(getContentViewByLayoutId())
        } else {
            setContentView(getContentView())
        }
        if (isNeedSetStatusBarMode()) {
            setStatusBarDarkMode()
        }
        afterSetContentView(savedInstanceState)
    }

    abstract fun getContentView(): View

    open fun getContentViewByLayoutId(): Int {
        return -1
    }

    open fun afterSetContentView(savedInstanceState: Bundle?) {

    }

    open fun isNeedSetStatusBarMode(): Boolean {
        return true
    }

    /**
     * 设置状态栏黑字白底
     */
    private fun setStatusBarDarkMode() {
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //            statusBarColor = Color.parseColor("#FFFFFF")
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}