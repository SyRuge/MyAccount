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
 * Created by xuchongxiang on 2021年01月14日.
 */
class PayInfoDetailModel : ViewModel() {
    val payInfo = MutableLiveData<PayInfoBean>()
    val updateInfo = MutableLiveData<Int>()
    val deletePayInfo = MutableLiveData<Int>()

    fun getPayInfoById(id: Long) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.getPayInfoById(id)
            }
            payInfo.value = deferred.await()
        }
    }

    fun updatePayInfo(bean: PayInfoBean) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.updatePayInfo(bean)
            }
            updateInfo.value = deferred.await()
            getPayInfoById(bean.id)
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