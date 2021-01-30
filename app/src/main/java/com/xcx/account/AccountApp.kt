package com.xcx.account

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 * Created by SyRuge on 2020年12月18日.
 */
class AccountApp : Application() {

    companion object {
        var me: AccountApp by Delegates.notNull()
        var appContext: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        me = this
        appContext = applicationContext
    }
}