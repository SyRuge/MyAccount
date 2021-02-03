package com.xcx.account.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.utils.monthEndTime
import com.xcx.account.utils.monthStartTime
import com.xcx.account.utils.yearEndTime
import com.xcx.account.utils.yearStartTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by SyRuge on 2021年02月02日.
 */
class PayCountDetailModel : ViewModel() {
    val categoryInfo = MutableLiveData<MutableList<PayInfoBean>>()
    val dayTrendPayInfo = PayRepository.getPayInfoByTimeRangeWithLD(monthStartTime(), monthEndTime())
    val monthPayInfo = PayRepository.getPayInfoByTimeRangeWithLD(yearStartTime(), yearEndTime())

    fun getCategoryByTimeRange(startTime: Long, endTime: Long){
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.getPayInfoByTimeRange(startTime, endTime)
            }
            categoryInfo.value = deferred.await()
        }
    }

    fun getDayTrendByTimeRange(startTime: Long, endTime: Long){
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.getPayInfoByTimeRange(startTime, endTime)
            }
            categoryInfo.value = deferred.await()
        }
    }

    fun getMonthPayByTimeRange(startTime: Long, endTime: Long){
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.getPayInfoByTimeRange(startTime, endTime)
            }
            categoryInfo.value = deferred.await()
        }
    }
}