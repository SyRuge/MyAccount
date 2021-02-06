package com.xcx.account.repository.database

import androidx.lifecycle.LiveData
import com.xcx.account.repository.database.database.PayDataBaseHelper
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.utils.yearEndTime
import com.xcx.account.utils.yearStartTime

/**
 * Created by SyRuge on 2020年12月22日.
 */
class PayRepository {

    companion object {
        const val TAG = "PayRepository"

        /**
         * query by id
         */
        suspend fun getPayInfoById(id: Long): PayInfoBean {
            return PayDataBaseHelper.db.payDao().getPayInfoById(id)
        }

        /**
         * insert
         */
        suspend fun addPayInfo(bean: PayInfoBean): Long {
            return PayDataBaseHelper.db.payDao().insertPayInfo(bean)
        }

        /**
         * delete
         */
        suspend fun deletePayInfo(bean: PayInfoBean): Int {
            return PayDataBaseHelper.db.payDao().deletePayInfo(bean)
        }

        /**
         * update
         */
        suspend fun updatePayInfo(bean: PayInfoBean): Int {
            return PayDataBaseHelper.db.payDao().updatePayInfo(bean)
        }

        /**
         * query
         */
        suspend fun getAllPayInfo(): MutableList<PayInfoBean> {
            return PayDataBaseHelper.db.payDao().getAllPayInfo()
        }


        /**
         * query
         */
        fun getPayInfoByTimeRangeWithLD(
            startTime: Long,
            endTime: Long,
        ): LiveData<MutableList<PayInfoBean>> {
            return PayDataBaseHelper.db.payDao().getPayInfoByTimeRangeWithLD(startTime, endTime)
        }

        /**
         * query
         */
        fun getCurYearPayInfo(): LiveData<MutableList<PayInfoBean>> {
            return PayDataBaseHelper.db.payDao().getCurYearPayInfo(yearStartTime(), yearEndTime())
        }

        /**
         * query
         */
        fun getPayInfoByCategory(
            payCategory: String,
            startTime: Long,
            endTime: Long,
        ): MutableList<PayInfoBean> {
            return PayDataBaseHelper.db.payDao()
                .getPayInfoByCategory(startTime, endTime, payCategory)
        }

        /**
         * query
         */
        fun getPayInfoByTimeRange(startTime: Long, endTime: Long): MutableList<PayInfoBean> {
            return PayDataBaseHelper.db.payDao().getPayInfoByTimeRange(startTime, endTime)
        }
    }
}