package com.xcx.account.viewmodel

import androidx.lifecycle.ViewModel
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.utils.monthEndTime
import com.xcx.account.utils.monthStartTime
import com.xcx.account.utils.yearEndTime
import com.xcx.account.utils.yearStartTime

/**
 * Created by SyRuge on 2021年02月02日.
 */
class PayCountDetailModel : ViewModel() {
    val categoryInfo = PayRepository.getCurYearPayInfo()
    val dayTrendPayInfo = PayRepository.getPayInfoByTimeRange(monthStartTime(), monthEndTime())
    val monthPayInfo = PayRepository.getPayInfoByTimeRange(yearStartTime(), yearEndTime())
}