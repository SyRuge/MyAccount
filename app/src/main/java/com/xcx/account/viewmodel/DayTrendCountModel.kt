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
 * Created by SyRuge on 2021年02月04日.
 */
class DayTrendCountModel : ViewModel() {
    val dayTrendPayInfo = MutableLiveData<MutableList<PayInfoBean>>()

    fun getDayTrendByTimeRange(startTime: Long, endTime: Long) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.getPayInfoByTimeRange(startTime, endTime)
            }
            dayTrendPayInfo.value = deferred.await()
        }
    }
}