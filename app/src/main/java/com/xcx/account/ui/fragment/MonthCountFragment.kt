package com.xcx.account.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcx.account.R
import com.xcx.account.adapter.PayCountAdapter
import com.xcx.account.bean.PayCountBean
import com.xcx.account.databinding.FragmentMonthCountBinding
import com.xcx.account.ui.view.ChartHelper
import com.xcx.account.utils.*
import com.xcx.account.viewmodel.CategoryCountModel
import com.xcx.account.viewmodel.MonthCountModel
import java.util.*


class MonthCountFragment : BaseFragment() {

    private var _binding: FragmentMonthCountBinding? = null
    private val binding get() = _binding!!
    private val monthModel: MonthCountModel by lazy {
        ViewModelProvider(this).get(MonthCountModel::class.java)
    }

    private var startTime = yearStartTime()
    private var endTime = yearEndTime()
    private var categoryAdapter: PayCountAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMonthCountBinding.inflate(inflater, container, false)
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
        val year = c.get(Calendar.YEAR)
        val text = "${year}年"
        binding.tvToolbarTitle.text = getString(R.string.pay_count_detail_title)
        binding.tvPayCountTitle.text = getString(R.string.month_count)
        binding.tvCurTime.text = text
        ChartHelper.initBarChart(binding.bcPayCount)
    }

    private fun initData() {
        monthModel.getMonthPayByTimeRange(startTime, endTime)
    }

    private fun initListener() {
        observeBarChartData()
        binding.flPreTime.setOnClickListener {
            updateStartAndEndTime(false)
            initData()
        }
        binding.flNextTime.setOnClickListener {
            updateStartAndEndTime(true)
            initData()
        }

    }

    private fun observeBarChartData() {
        monthModel.monthPayInfo.observe(viewLifecycleOwner) { list ->
            val barList = ChartHelper.handleBarChartData(list)
            ChartHelper.setBarChartData(binding.bcPayCount, barList)
            val money = "-¥${getMoneyWithTwoDecimal(ChartHelper.barChartTotalMoney)}"
            binding.tvPayCount.text = money
            val adapterList = mutableListOf<PayCountBean>()
            val c = Calendar.getInstance()
            for (i in barList.size - 1 downTo 0) {
                if (barList[i].payMoney > 0) {
                    c.timeInMillis = barList[i].payTime
                    val title = "${c.get(Calendar.YEAR)}年${c.get(Calendar.MONTH + 1)}月"
                    val bean = PayCountBean(
                        title,
                        barList[i].payMoney,
                        ChartHelper.barChartTotalMoney,
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
            endTime = nextYearStartTime(endTime)
        } else {
            endTime = startTime
            startTime = preYearStartTime(startTime)
        }
        val arr = getMonthAndYear(startTime)
        val text = "${arr[0]}年"
        binding.tvCurTime.text = text
    }

    private fun setDataToRecyclerView(list: MutableList<PayCountBean>) {
        if (categoryAdapter == null) {
            binding.rvPayCountDetail.layoutManager = LinearLayoutManager(context)
            categoryAdapter = PayCountAdapter(payList = list)
            binding.rvPayCountDetail.adapter = categoryAdapter
            categoryAdapter?.setOnItemClickListener {
                startPayListActivity(
                    SHOW_CATEGORY_LIST,
                    it.startTime,
                    it.endTime,
                    it.categoryName
                )
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
