package com.xcx.account.repository.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xcx.account.repository.database.dao.PayDao
import com.xcx.account.repository.database.table.PayInfoBean

/**
 * Created by xuchongxiang on 2020年12月21日.
 */
@Database(entities = [PayInfoBean::class], version = 1)
abstract class PayDataBase : RoomDatabase() {
    abstract fun payDao(): PayDao
}