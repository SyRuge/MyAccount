package com.xcx.account.viewmodel

import androidx.lifecycle.*
import com.xcx.account.bean.HomePayBean
import com.xcx.account.repository.database.database.PayDataBaseHelper

/**
 * Created by xuchongxiang on 2020年12月22日.
 */
class PayViewModel : ViewModel() {
    private val payInfo = PayDataBaseHelper.db.payDao().getAllPayInfoLD()

    val allPayInfo: LiveData<List<HomePayBean>> = payInfo.switchMap {

        val map = it.map { bean ->
            HomePayBean(
                bean.id,
                bean.payId,
                bean.paySellerName,
                bean.payMoney,
                bean.payCategory,
                bean.payTime,
                bean.payDate
            )
        }

        MutableLiveData(map)
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