package com.xcx.account.repository.database.database

import androidx.room.Room
import com.xcx.account.AccountApp

/**
 * Created by SyRuge on 2020年12月21日.
 */
class PayDataBaseHelper {
    companion object {
        val db by lazy {
            Room.databaseBuilder(AccountApp.appContext, PayDataBase::class.java,"pay-info.db").build()
        }
    }
}