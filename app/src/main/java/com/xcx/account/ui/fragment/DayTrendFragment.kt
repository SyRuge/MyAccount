package com.xcx.account.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcx.account.R
import com.xcx.account.adapter.PayCountAdapter
import com.xcx.account.bean.PayCountBean
import com.xcx.account.databinding.FragmentDayTrendBinding
import com.xcx.account.ui.view.ChartHelper
import com.xcx.account.utils.*
import com.xcx.account.viewmodel.DayTrendCountModel
import java.text.SimpleDateFormat
import java.util.*


class DayTrendFragment : BaseFragment() {

    private var _binding: FragmentDayTrendBinding? = null
    private val binding get() = _binding!!
    private val dayTrendModel: DayTrendCountModel by lazy {
        ViewModelProvider(this).get(DayTrendCountModel::class.java)
    }

    private var startTime = monthStartTime()
    private var endTime = monthEndTime()
    private var categoryAdapter: PayCountAdapter? = null
    private val format = SimpleDateFormat("yy.MM")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayTrendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        val c = Calendar.getInstance()
        val month = c.get(Calendar.MONTH) + 1
        val text = "${month}月"
        binding.tvToolbarTitle.text = getString(R.string.pay_count_detail_title)
        binding.tvPayCountTitle.text = getString(R.string.day_trend)
        binding.tvCurTime.text = text
        ChartHelper.initLineChart(binding.lcPayCount, context)
    }

    private fun initData() {
        dayTrendModel.getDayTrendByTimeRange(startTime, endTime)
    }

    private fun initListener() {
        observeLineChartData()
        binding.flPreTime.setOnClickListener {
            updateStartAndEndTime(false)
            initData()
        }
        binding.flNextTime.setOnClickListener {
            updateStartAndEndTime(true)
            initData()
        }
    }

    private fun observeLineChartData() {
        dayTrendModel.dayTrendPayInfo.observe(viewLifecycleOwner) { list ->
            val lineList = ChartHelper.handleLineChartData(list)
            ChartHelper.setLineChartData(binding.lcPayCount, lineList)
            val money = "-¥${getMoneyWithTwoDecimal(ChartHelper.lineChartTotalMoney)}"
            binding.tvPayCount.text = money

            val adapterList = mutableListOf<PayCountBean>()
            val c = Calendar.getInstance()
            for (i in lineList.size - 1 downTo 0) {
                if (lineList[i].payMoney > 0) {
                    c.timeInMillis = lineList[i].payTime
                    val title =
                        "${c.get(Calendar.YEAR)}年${c.get(Calendar.MONTH + 1)}月${c.get(Calendar.DAY_OF_MONTH)}日"
                    val bean = PayCountBean(
                        title,
                        lineList[i].payMoney,
                        ChartHelper.lineChartTotalMoney,
                        startTime,
                        endTime,
                        resources.getColor(R.color.pink_color_500, null),
                        0
                    )
                    adapterList.add(bean)
                }
            }
            setDataToRecyclerView(adapterList)
        }
    }


    private fun updateStartAndEndTime(isNextTime: Boolean) {
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

    private fun setDataToRecyclerView(list: MutableList<PayCountBean>) {
        if (categoryAdapter == null) {
            binding.rvPayCountDetail.layoutManager = LinearLayoutManager(context)
            categoryAdapter = PayCountAdapter(payList = list)
            binding.rvPayCountDetail.adapter = categoryAdapter
            categoryAdapter?.setOnItemClickListener {
                startPayListActivity(SHOW_TIME_RANGE_LIST, it.startTime, it.endTime)
            }
        } else {
            categoryAdapter?.updatePayInfoData(list)
        }
    }


    private fun getMonthAndYear(time: Long): IntArray {
        val c = Calendar.getInstance()
        c.timeInMillis = time
        return intArrayOf(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
