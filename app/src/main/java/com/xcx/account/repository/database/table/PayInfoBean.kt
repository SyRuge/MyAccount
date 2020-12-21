package com.xcx.account.repository.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by xuchongxiang on 2020年12月21日.
 */
@Entity(tableName = "pay_info")
data class PayInfoBean(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "payId") val payId: String,
    @ColumnInfo(name = "paySellerName") val paySellerName: String,
    @ColumnInfo(name = "payMoney") val payMoney: Double,
    @ColumnInfo(name = "payCategory") val payCategory: String,
    @ColumnInfo(name = "payDate") val payDate: String
)
