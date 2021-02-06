package com.xcx.account.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.repository.database.table.PayInfoBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by SyRuge on 2020年12月22日.
 */
class PayListViewModel : ViewModel() {
    val deletePayInfo = MutableLiveData<Int>()
    val categoryPayInfo = MutableLiveData<MutableList<PayInfoBean>>()
    val timeRangePayInfo = MutableLiveData<MutableList<PayInfoBean>>()

    fun getPayInfoByCategoryAndTime(payCategory: String, startTime: Long, endTime: Long) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.getPayInfoByCategory(payCategory, startTime, endTime)
            }
            categoryPayInfo.value = deferred.await()
        }
    }

    fun getPayInfoByTimeRange(startTime: Long, endTime: Long) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.getPayInfoByTimeRange(startTime, endTime)
            }
            timeRangePayInfo.value = deferred.await()
        }
    }

    fun deletePayInfoById(bean: PayInfoBean) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.deletePayInfo(bean)
            }
            deletePayInfo.value = deferred.await()
        }
    }
}