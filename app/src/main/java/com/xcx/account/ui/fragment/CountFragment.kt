package com.xcx.account.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.xcx.account.R
import com.xcx.account.databinding.FragmentCountBinding
import com.xcx.account.ui.activity.PayCountDetailActivity
import com.xcx.account.ui.view.ChartHelper
import com.xcx.account.utils.*
import com.xcx.account.viewmodel.CountViewModel


class CountFragment : BaseFragment() {

    private val TAG = "CountFragment"
    private var _binding: FragmentCountBinding? = null
    private val binding get() = _binding!!
    private val countModel: CountViewModel by lazy {
        ViewModelProvider(this).get(CountViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        binding.tvToolbarTitle.text = getString(R.string.tab_count)
    }

    private fun initData() {
        ChartHelper.initPieChart(binding.pcCategoryCount)
        ChartHelper.initLineChart(binding.lcDayTrendCount, context)
        ChartHelper.initBarChart(binding.bcMonthCount)
    }

    private fun initListener() {
        observePieChartData()
        observeLineChartData()
        observeBarChartData()
        binding.ivPcDetail.setOnClickListener {
            val intent = Intent(context, PayCountDetailActivity::class.java).apply {
                putExtra(PAY_COUNT_DETAIL_TYPE_KEY, SHOW_PIECHART_DETAIL)
            }
            startActivity(intent)
        }
        binding.ivLcDetail.setOnClickListener {
            val intent = Intent(context, PayCountDetailActivity::class.java).apply {
                putExtra(PAY_COUNT_DETAIL_TYPE_KEY, SHOW_LINECHART_DETAIL)
            }
            startActivity(intent)
        }
        binding.ivBcDetail.setOnClickListener {
            val intent = Intent(context, PayCountDetailActivity::class.java).apply {
                putExtra(PAY_COUNT_DETAIL_TYPE_KEY, SHOW_BARCHART_DETAIL)
            }
            startActivity(intent)
        }
    }

    private fun observePieChartData() {
        countModel.categoryInfo.observe(viewLifecycleOwner) { list ->
            val pieList = ChartHelper.handlePieChartData(list)
            ChartHelper.setPieChartData(binding.pcCategoryCount, pieList)
            val money = "-¥${getMoneyWithTwoDecimal(ChartHelper.pieChartTotalMoney)}"
            binding.tvCategoryCount.text = money
        }

    }

    private fun observeLineChartData() {
        countModel.dayTrendPayInfo.observe(viewLifecycleOwner) { list ->
            val lineList = ChartHelper.handleLineChartData(list)
            ChartHelper.setLineChartData(binding.lcDayTrendCount, lineList)
            val money = "-¥${getMoneyWithTwoDecimal(ChartHelper.lineChartTotalMoney)}"
            binding.tvDayTrendCount.text = money
        }
    }

    private fun observeBarChartData() {
        countModel.monthPayInfo.observe(viewLifecycleOwner) { list ->
            val barList = ChartHelper.handleBarChartData(list)
            ChartHelper.setBarChartData(binding.bcMonthCount, barList)
            val money = "-¥${getMoneyWithTwoDecimal(ChartHelper.barChartTotalMoney)}"
            binding.tvMonthCount.text = money
        }
    }

    private fun setTotalMoneyText(totalMoney: Long) {
        val money = "-¥${getMoneyWithTwoDecimal(totalMoney)}"
        binding.tvCategoryCount.text = money
        binding.tvMonthCount.text = money
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}