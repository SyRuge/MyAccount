package com.xcx.account.ui.activity


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcx.account.R
import com.xcx.account.adapter.PayCountAdapter
import com.xcx.account.bean.PayCountBean
import com.xcx.account.databinding.ActivityPayCountDetail2Binding
import com.xcx.account.databinding.ActivityPayCountDetailBinding
import com.xcx.account.ui.view.ChartHelper
import com.xcx.account.utils.*
import com.xcx.account.viewmodel.PayCountDetailModel
import java.text.SimpleDateFormat
import java.util.*

@Deprecated("will remove")
class PayCountDetailActivity2 : BaseActivity() {

    private val TAG = "PayCountDetailActivity"
    private lateinit var binding: ActivityPayCountDetail2Binding
    private val payModel: PayCountDetailModel by viewModels()

    private var showCountType = 0
    private var startTime = monthStartTime()
    private var endTime = monthEndTime()
    private val format = SimpleDateFormat("yy.MM")


    override fun getContentView(): View {
        binding = ActivityPayCountDetail2Binding.inflate(layoutInflater)
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
                startTime = yearStartTime()
                endTime = yearEndTime()
                ChartHelper.initBarChart(binding.bcPayCount)
                observeBarChartData()
            }
        }
    }

    private fun initData() {
        val adapter = PayCountAdapter(this, mutableListOf())
        binding.rvPayCountDetail.layoutManager = LinearLayoutManager(this)
        binding.rvPayCountDetail.adapter = adapter
        refreshChartData()
    }

    private fun initListener() {
        binding.flPreTime.setOnClickListener {
            updateStartAndEndTime(false)
            refreshChartData()
        }
        binding.flNextTime.setOnClickListener {
            updateStartAndEndTime(true)
            refreshChartData()
        }
    }

    private fun updateStartAndEndTime(isNextTime: Boolean) {
        when (showCountType) {
            SHOW_PIECHART_DETAIL -> {
                if (isNextTime) {
                    startTime = endTime
                    endTime = nextMonthStartTime(endTime)
                } else {
                    endTime = startTime
                    startTime = preMonthStartTime(startTime)
                }
                val arr = getMonthAndYear(startTime)
                if (startTime in yearStartTime()..yearEndTime()) {
                    val text = "${arr[1]}月"
                    binding.tvCurTime.text = text
                } else {
                    binding.tvCurTime.text = format.format(startTime)
                }
            }
            SHOW_LINECHART_DETAIL -> {
                if (isNextTime) {
                    startTime = endTime
                    endTime = nextMonthStartTime(endTime)
                } else {
                    endTime = startTime
                    startTime = preMonthStartTime(startTime)
                }
                val arr = getMonthAndYear(startTime)
                if (startTime in yearStartTime()..yearEndTime()) {
                    val text = "${arr[1]}月"
                    binding.tvCurTime.text = text
                } else {
                    binding.tvCurTime.text = format.format(startTime)
                }
            }
            SHOW_BARCHART_DETAIL -> {
                if (isNextTime) {
                    startTime = endTime
                    endTime = nextYearStartTime(endTime)
                } else {
                    endTime = startTime
                    startTime = preYearStartTime(startTime)
                }
                val arr = getMonthAndYear(startTime)
                val text = "${arr[0]}年"
                binding.tvCurTime.text = text
            }
        }
    }

    private fun refreshChartData() {
        when (showCountType) {
            SHOW_PIECHART_DETAIL -> {
                payModel.getCategoryByTimeRange(startTime, endTime)
            }
            SHOW_LINECHART_DETAIL -> {
                payModel.getDayTrendByTimeRange(startTime, endTime)
            }
            SHOW_BARCHART_DETAIL -> {
                payModel.getMonthPayByTimeRange(startTime, endTime)
            }
        }
    }

    private fun getMonthAndYear(time: Long): IntArray {
        val c = Calendar.getInstance()
        c.timeInMillis = time
        return intArrayOf(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1)
    }

    private fun observePieChartData() {
        payModel.categoryInfo.observe(this) { list ->
            val pieList = ChartHelper.handlePieChartData(list)
            ChartHelper.setPieChartData(binding.pcPayCount, pieList)
            val money = "-¥${getMoneyWithTwoDecimal(ChartHelper.pieChartTotalMoney)}"
            binding.tvPayCount.text = money
            pieList.sortByDescending {
                it.payMoney
            }
            val adapterList = mutableListOf<PayCountBean>()
            pieList.forEach {
                val bean = PayCountBean(
                    it.payCategory,
                    it.payMoney,
                    ChartHelper.pieChartTotalMoney,
                    startTime,
                    endTime,
                    Color.RED,
                    showCountType
                )
                adapterList.add(bean)
            }
            setDataToRecyclerView(adapterList)
        }

    }

    private fun observeLineChartData() {
        payModel.dayTrendPayInfo.observe(this) { list ->
            val lineList = ChartHelper.handleLineChartData(list)
            ChartHelper.setLineChartData(binding.lcPayCount, lineList)
            val money = "-¥${getMoneyWithTwoDecimal(ChartHelper.lineChartTotalMoney)}"
            binding.tvPayCount.text = money

            val adapterList = mutableListOf<PayCountBean>()
            for (i in lineList.size - 1 downTo 0) {
                if (lineList[i].payMoney > 0) {
                    val bean = PayCountBean(
                        (i + 1).toString(),
                        lineList[i].payMoney,
                        ChartHelper.lineChartTotalMoney,
                        startTime,
                        endTime,
                        Color.YELLOW,
                        showCountType
                    )
                    adapterList.add(bean)
                }
            }
            setDataToRecyclerView(adapterList)
        }
    }

    private fun observeBarChartData() {
        payModel.monthPayInfo.observe(this) { list ->
            val barList = ChartHelper.handleBarChartData(list)
            ChartHelper.setBarChartData(binding.bcPayCount, barList)
            val money = "-¥${getMoneyWithTwoDecimal(ChartHelper.barChartTotalMoney)}"
            binding.tvPayCount.text = money
            val adapterList = mutableListOf<PayCountBean>()
            for (i in barList.size - 1 downTo 0) {
                if (barList[i].payMoney > 0) {
                    val bean = PayCountBean(
                        (i + 1).toString(),
                        barList[i].payMoney,
                        ChartHelper.barChartTotalMoney,
                        startTime,
                        endTime,
                        Color.BLUE,
                        showCountType
                    )
                    adapterList.add(bean)
                }
            }
            setDataToRecyclerView(adapterList)
        }
    }

    private fun setDataToRecyclerView(list: MutableList<PayCountBean>) {
        binding.rvPayCountDetail.layoutManager = LinearLayoutManager(this)
        var adapter = PayCountAdapter(payList = list)
        binding.rvPayCountDetail.adapter = adapter
        adapter.setOnItemClickListener {
            when (showCountType) {
                SHOW_PIECHART_DETAIL -> {
                    startPayListActivity(
                        SHOW_CATEGORY_LIST,
                        it.startTime,
                        it.endTime,
                        it.categoryName
                    )
                }
                SHOW_LINECHART_DETAIL, SHOW_BARCHART_DETAIL -> {
                    startPayListActivity(SHOW_TIME_RANGE_LIST, it.startTime, it.endTime)
                }
            }
        }

    }
}
