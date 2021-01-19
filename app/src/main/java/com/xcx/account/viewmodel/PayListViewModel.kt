package com.xcx.account.viewmodel

import androidx.lifecycle.*
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.repository.database.table.PayInfoBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by xuchongxiang on 2020年12月22日.
 */
class PayListViewModel : ViewModel() {
    val payInfo = PayRepository.getCurYearPayInfo()
    val deletePayInfo = MutableLiveData<Int>()

    fun deletePayInfoById(bean: PayInfoBean) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.deletePayInfo(bean)
            }
            deletePayInfo.value = deferred.await()
        }
    }
}