package com.xcx.account.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.xcx.account.bean.PayJsonBean
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.repository.database.database.PayDataBaseHelper
import com.xcx.account.utils.readFileByResolver
import com.xcx.account.utils.writeFileByResolver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by SyRuge on 2021年01月25日.
 */
class MyViewModel : ViewModel() {
    val backupStatus = MutableLiveData<Boolean>()
    val restoreStatus = MutableLiveData<Boolean>()


    fun backupPayInfoJsonToLocal(uri: Uri) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                val list = PayRepository.getAllPayInfo()
                val bean = PayJsonBean(list)
                val gson = Gson()
                val json = gson.toJson(bean)
                writeFileByResolver(uri, json.toByteArray())
            }
            backupStatus.value = deferred.await()
        }
    }

    fun restorePayInfoJsonFromLocal(uri: Uri) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                val json = readFileByResolver(uri)
                val gson = Gson()
                val bean = gson.fromJson(json, PayJsonBean::class.java)
                PayDataBaseHelper.db.clearAllTables()
                bean.payInfos.forEach {
                    it.id = 0L
                    PayRepository.addPayInfo(it)
                }
                true
            }
            restoreStatus.value = deferred.await()
        }
    }
}