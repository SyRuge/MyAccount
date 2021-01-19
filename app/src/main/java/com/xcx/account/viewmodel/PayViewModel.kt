package com.xcx.account.viewmodel

import androidx.lifecycle.*
import com.xcx.account.bean.HomePayBean
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.repository.database.table.PayInfoBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by xuchongxiang on 2020年12月22日.
 */
class PayViewModel : ViewModel() {
    private val payInfo = PayRepository.getCurYearPayInfo()
    val deletePayInfo = MutableLiveData<Int>()


    val allPayInfo: LiveData<List<HomePayBean>> = payInfo.switchMap {

        val map = it.map { bean ->
            HomePayBean(
                bean.id,
                bean.payId,
                bean.paySellerName,
                bean.payMoney,
                bean.payCategory,
                bean.payTime,
                bean.payDate,
                bean.payNote
            )
        }

        MutableLiveData(map)
    }

    fun deletePayInfoById(bean: PayInfoBean) {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                PayRepository.deletePayInfo(bean)
            }
            deletePayInfo.value = deferred.await()
        }
    }

    /*val allPayInfo: LiveData<MutableList<HomePayBean>> = Transformations.map(payInfo) {
        it.map { bean ->
            HomePayBean(
                bean.id,
                bean.payId,
                bean.paySellerName,
                bean.payMoney,
                bean.payCategory,
                bean.payTime,
                bean.payDate
            )
        }.toMutableList()
    }*/


}