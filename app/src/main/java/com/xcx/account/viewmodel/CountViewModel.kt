package com.xcx.account.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.utils.monthEndTime
import com.xcx.account.utils.monthStartTime
import com.xcx.account.utils.yearEndTime
import com.xcx.account.utils.yearStartTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by SyRuge on 2021年01月14日.
 */
class CountViewModel : ViewModel() {

    val categoryInfo = PayRepository.getPayInfoByTimeRangeWithLD(monthStartTime(), monthEndTime())
    val dayTrendPayInfo =
        PayRepository.getPayInfoByTimeRangeWithLD(monthStartTime(), monthEndTime())
    val monthPayInfo = PayRepository.getPayInfoByTimeRangeWithLD(yearStartTime(), yearEndTime())

    @Deprecated("will delete")
    fun getDayTrendPayInfo(startTime: Long, endTime: Long) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.getPayInfoByTimeRangeWithLD(startTime, endTime)
            }
            //dayTrendPayInfo.value = deferred.await()
        }
    }

    @Deprecated("will delete")
    fun getMonthPayInfo(startTime: Long, endTime: Long) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.getPayInfoByTimeRangeWithLD(startTime, endTime)
            }
            //monthPayInfo.value = deferred.await()
        }
    }
}