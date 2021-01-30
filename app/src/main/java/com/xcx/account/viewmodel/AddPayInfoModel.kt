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
 * Created by SyRuge on 2021年01月14日.
 */
class AddPayInfoModel : ViewModel() {
    val addPayInfo = MutableLiveData<Long>()

    fun addPayInfo(bean: PayInfoBean) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.addPayInfo(bean)
            }
            addPayInfo.value = deferred.await()
        }
    }
}