package com.xcx.account.repository.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by SyRuge on 2020年12月21日.
 */
@Entity(tableName = "pay_info")
data class PayInfoBean(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,
    @ColumnInfo(name = "payId") var payId: String,
    @ColumnInfo(name = "paySellerName") var paySellerName: String,
    @ColumnInfo(name = "payMoney") var payMoney: Long,
    @ColumnInfo(name = "payCategory") var payCategory: String,
    @ColumnInfo(name = "payTime") var payTime: Long,
    @ColumnInfo(name = "payDate") var payDate: String,
    @ColumnInfo(name = "payNote") var payNote: String
)
