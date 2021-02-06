package com.xcx.account.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.xcx.account.R
import com.xcx.account.ui.fragment.CategoryCountFragment
import com.xcx.account.ui.fragment.DayTrendFragment
import com.xcx.account.ui.fragment.MonthCountFragment
import com.xcx.account.utils.PAY_COUNT_DETAIL_TYPE_KEY
import com.xcx.account.utils.SHOW_BARCHART_DETAIL
import com.xcx.account.utils.SHOW_LINECHART_DETAIL
import com.xcx.account.utils.SHOW_PIECHART_DETAIL

class PayCountDetailActivity : BaseActivity() {

    private val TAG = "PayCountDetailActivity"
    private var showCountType = 0


    override fun getContentView(): View {
        return TextView(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_pay_count_detail
    }

    override fun afterSetContentView(savedInstanceState: Bundle?) {
        super.afterSetContentView(savedInstanceState)
        showCountType = intent.getIntExtra(PAY_COUNT_DETAIL_TYPE_KEY, SHOW_PIECHART_DETAIL)
        initView()
    }

    private fun initView() {
        when (showCountType) {
            SHOW_PIECHART_DETAIL -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.cl_count_root, CategoryCountFragment(), "category")
                    .commitNowAllowingStateLoss()
            }
            SHOW_LINECHART_DETAIL -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.cl_count_root, DayTrendFragment(), "dayTrend")
                    .commitNowAllowingStateLoss()
            }
            SHOW_BARCHART_DETAIL -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.cl_count_root, MonthCountFragment(), "month")
                    .commitNowAllowingStateLoss()
            }
        }
    }
}
