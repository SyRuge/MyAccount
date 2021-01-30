package com.xcx.account.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.xcx.account.databinding.FragmentCountBinding
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.ui.view.ChartHelper
import com.xcx.account.utils.*
import com.xcx.account.viewmodel.CountViewModel
import kotlinx.coroutines.*
import java.util.*


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
        binding.tvToolbarTitle.text = "统计"

    }

    private fun initData() {
        ChartHelper.initPieChart(binding.pcCategoryCount)
        ChartHelper.initLineChart(binding.lcDayTrendCount)
        ChartHelper.initBarChart(binding.bcMonthCount)
    }

    private fun initListener() {
        observePieChartData()
        observeLineChartData()
        observeBarChartData()
    }

    private fun observePieChartData() {

//        countModel.getCategoryData()
        countModel.categoryInfo.observe(viewLifecycleOwner) { list ->
            val sumList = list.groupBy { it.payCategory }
                .values.map {
                    it.reduce { acc, item ->
                        PayInfoBean(
                            acc.id,
                            acc.payId,
                            acc.paySellerName,
                            acc.payMoney + item.payMoney,
                            acc.payCategory,
                            acc.payTime,
                            acc.payDate,
                            acc.payNote
                        )
                    }
                }.toMutableList()
            if (sumList.isNullOrEmpty()) {
                return@observe
            }
            logd(TAG, "initPieChartData(): sumList is not empty")
            val pieEntries = mutableListOf<PieEntry>()
            var totalMoney = 0L
            sumList.forEach {
                totalMoney += it.payMoney
                pieEntries.add(PieEntry(it.payMoney.toFloat(), it.payCategory))
            }
            setTotalMoneyText(totalMoney)
            val pieDataSet = PieDataSet(pieEntries, "")
            ChartHelper.setPieChartData(binding.pcCategoryCount, pieDataSet)

        }

    }

    private fun observeLineChartData() {
        countModel.dayTrendPayInfo.observe(viewLifecycleOwner) { oriList ->
            val c = Calendar.getInstance()

            val map = oriList.groupBy {
                c.timeInMillis = it.payTime
                c.get(Calendar.DAY_OF_MONTH)
            }.toMutableMap()
            val calendar = Calendar.getInstance()
            val totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            for (i in 1..totalDays) {
                if (!map.containsKey(i)) {
                    calendar.set(Calendar.DAY_OF_MONTH, i)
                    map[i] = mutableListOf(
                        PayInfoBean(
                            0L, "", "", 0L,
                            "", calendar.timeInMillis, "", ""
                        )
                    )
                }
            }
            val list = map.values.map {
                it.reduce { acc, item ->
                    c.timeInMillis = item.payTime
                    PayInfoBean(
                        acc.id,
                        acc.payId,
                        acc.paySellerName,
                        acc.payMoney + item.payMoney,
                        acc.payCategory,
                        c.get(Calendar.DAY_OF_MONTH).toLong(),
                        acc.payDate,
                        acc.payNote
                    )
                }
            }.sortedBy {
                it.payTime
            }.toMutableList()


            if (list.isEmpty()) {
                return@observe
            }

            logd(TAG, "initLineChartData(): list is not empty")
            //设置数据
            val entries = mutableListOf<Entry>()

            for (i in list.indices) {
                logd(TAG, "initLineChartData() i: $i ,value: ${list[i]}")
                entries.add(Entry(i.toFloat(), list[i].payMoney.toFloat() / 100))
            }
            //一个LineDataSet就是一条线
            val lineDataSet = LineDataSet(entries, "")
            ChartHelper.setLineChartData(binding.lcDayTrendCount, lineDataSet)
        }
    }

    private fun observeBarChartData() {
        countModel.monthPayInfo.observe(viewLifecycleOwner) { oriList ->
            val c = Calendar.getInstance()

            val map = oriList.groupBy {
                c.timeInMillis = it.payTime
                c.get(Calendar.MONTH)
            }.toMutableMap()
            val calendar = Calendar.getInstance()
            val totalMonth = calendar.getActualMaximum(Calendar.MONTH)
            for (i in 0..totalMonth) {
                if (!map.containsKey(i)) {
                    calendar.set(Calendar.DAY_OF_MONTH, 1)
                    calendar.set(Calendar.MONTH, i)
                    map[i] = mutableListOf(
                        PayInfoBean(
                            0L, "", "", 0L,
                            "", calendar.timeInMillis, "", ""
                        )
                    )
                }
            }

            val list = map.values.map {
                it.reduce { acc, item ->
                    c.timeInMillis = item.payTime
                    PayInfoBean(
                        acc.id,
                        acc.payId,
                        acc.paySellerName,
                        acc.payMoney + item.payMoney,
                        acc.payCategory,
                        c.get(Calendar.MONTH).toLong(),
                        acc.payDate,
                        acc.payNote
                    )
                }
            }.sortedBy {
                it.payTime
            }.toMutableList()

            if (list.isEmpty()) {
                return@observe
            }
            logd(TAG, "initBarChartData(): list is not empty")
            val entries = mutableListOf<BarEntry>()
            // x是横坐标，表示位置，y是纵坐标，表示高度

            for (i in list.indices) {
                logd(TAG, "initBarChartData() i: $i ,value: ${list[i]}")
                entries.add(BarEntry(i.toFloat(), list[i].payMoney.toFloat() / 100))
            }
            val barDataSet = BarDataSet(entries, "")
            ChartHelper.setBarChartData(binding.bcMonthCount, barDataSet)
        }
    }

    private fun setTotalMoneyText(totalMoney: Long) {
        val money = "-¥${getMoneyWithTwoDecimal(totalMoney)}"
        binding.tvCategoryCount.text = money
        binding.tvDayTrendCount.text = money
        binding.tvMonthCount.text = money
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}