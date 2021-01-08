package com.xcx.account.repository.database

import com.xcx.account.repository.database.database.PayDataBaseHelper
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.utils.logd
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by xuchongxiang on 2020年12月22日.
 */
class PayRepository {

    companion object {
        val TAG = "PayRepository"

        /**
         * query by id
         */
        suspend fun getPayInfoById(id: Long) : PayInfoBean {
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
        fun deletePayInfo(bean: PayInfoBean) {
            MainScope().launch {
                async(Dispatchers.IO) {
                    PayDataBaseHelper.db.payDao().deletePayInfo(bean)
                }
            }
        }

        /**
         * update
         */
        suspend fun updatePayInfo(bean: PayInfoBean) {
            PayDataBaseHelper.db.payDao().updatePayInfo(bean)
        }

        /**
         * query
         */
        fun getAllPayInfo() {
            /*MainScope().launch {
                async(Dispatchers.IO) {
                    val payList = PayDataBaseHelper.db.payDao().getAllPayInfo()
                    payList.forEach {
                        logd(TAG, it.toString())
                    }
//                    logd(TAG, "insert rowId: $payList")
                }
            }*/
        }
    }
}