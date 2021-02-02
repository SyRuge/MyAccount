package com.xcx.account.ui.activity


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcx.account.R
import com.xcx.account.adapter.PayCountAdapter
import com.xcx.account.databinding.ActivityPayCountDetailBinding
import com.xcx.account.ui.view.ChartHelper
import com.xcx.account.utils.*
import com.xcx.account.viewmodel.PayCountDetailModel

class PayCountDetailActivity : BaseActivity() {

    private val TAG = "PayCountDetailActivity"
    private lateinit var binding: ActivityPayCountDetailBinding
    private val payModel:PayCountDetailModel by viewModels()

    private var showCountType = 0

    override fun getContentView(): View {
        binding = ActivityPayCountDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun afterSetContentView(savedInstanceState: Bundle?) {
        super.afterSetContentView(savedInstanceState)
        showCountType = intent.getIntExtra(PAY_COUNT_DETAIL_TYPE_KEY, 0)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        binding.tvToolbarTitle.text = getString(R.string.pay_count_detail_title)
        when (showCountType) {
            SHOW_PIECHART_DETAIL -> {
                binding.tvPayCountTitle.text = getString(R.string.category_count)
                binding.pcPayCount.visibility = View.VISIBLE
                ChartHelper.initPieChart(binding.pcPayCount)
                observePieChartData()
            }
            SHOW_LINECHART_DETAIL -> {
                binding.tvPayCountTitle.text = getString(R.string.day_trend)
                binding.lcPayCount.visibility = View.VISIBLE
                ChartHelper.initLineChart(binding.lcPayCount, this)
                observeLineChartData()
            }
            SHOW_BARCHART_DETAIL -> {
                binding.tvPayCountTitle.text = getString(R.string.month_count)
                binding.bcPayCount.visibility = View.VISIBLE
                ChartHelper.initBarChart(binding.bcPayCount)
                observeBarChartData()
            }
        }
    }

    private fun initData() {

        val adapter = PayCountAdapter(this, mutableListOf())
        binding.rvPayCountDetail.layoutManager = LinearLayoutManager(this)
        binding.rvPayCountDetail.adapter = adapter
    }

    private fun initListener() {

    }

    private fun observePieChartData() {
        payModel.categoryInfo.observe(this) { list ->
            ChartHelper.setPieChartData(binding.pcPayCount, list)
            val money = "-¥${getMoneyWithTwoDecimal(ChartHelper.pieChartTotalMoney)}"
            binding.tvPayCount.text = money
        }

    }

    private fun observeLineChartData() {
        payModel.dayTrendPayInfo.observe(this) { oriList ->
            ChartHelper.setLineChartData(binding.lcPayCount, oriList)
            val money = "-¥${getMoneyWithTwoDecimal(ChartHelper.lineChartTotalMoney)}"
            binding.tvPayCount.text = money
        }
    }

    private fun observeBarChartData() {
        payModel.monthPayInfo.observe(this) { oriList ->
            ChartHelper.setBarChartData(binding.bcPayCount, oriList)
            val money = "-¥${getMoneyWithTwoDecimal(ChartHelper.barChartTotalMoney)}"
            binding.tvPayCount.text = money
        }
    }

}
