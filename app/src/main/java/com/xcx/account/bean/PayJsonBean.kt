package com.xcx.account.bean

import com.xcx.account.repository.database.table.PayInfoBean

/**
 * Create By SyRuge at 2021-01-22
 */
data class PayJsonBean(var payInfos: MutableList<PayInfoBean>) : BaseDataBean()