package com.xcx.account.repository.database.dao

import androidx.room.*
import com.xcx.account.repository.database.table.PayInfoBean

/**
 * Created by xuchongxiang on 2020年12月21日.
 */
@Dao
interface PayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayInfo(bean: PayInfoBean)

    @Delete
    suspend fun deletePayInfo(bean: PayInfoBean)

    @Update
    suspend fun updatePayInfo(bean: PayInfoBean)

    @Query("SELECT * FROM pay_info")
    suspend fun getAllPayInfo(): MutableList<PayInfoBean>
}