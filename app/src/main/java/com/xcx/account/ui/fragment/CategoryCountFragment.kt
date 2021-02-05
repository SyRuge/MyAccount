package com.xcx.account.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcx.account.R
import com.xcx.account.adapter.PayCountAdapter
import com.xcx.account.bean.PayCountBean
import com.xcx.account.databinding.FragmentCategoryCountBinding
import com.xcx.account.ui.view.ChartHelper
import com.xcx.account.utils.*
import com.xcx.account.viewmodel.CategoryCountModel
import java.text.SimpleDateFormat
import java.util.*


class CategoryCountFragment : BaseFragment() {

    private var _binding: FragmentCategoryCountBinding? = null
    private val binding get() = _binding!!
    private val categoryModel: CategoryCountModel by lazy {
        ViewModelProvider(this).get(CategoryCountModel::class.java)
    }

    private var startTime = monthStartTime()
    private var endTime = monthEndTime()
    private var categoryAdapter: PayCountAdapter? = null
    private val format = SimpleDateFormat("yy.MM")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCategoryCountBinding.inflate(inflater, container, false)
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
        binding.tvPayCountTitle.text = getString(R.string.category_count)
        binding.tvCurTime.text = text
        ChartHelper.initPieChart(binding.pcPayCount)
    }

    private fun initData() {
        categoryModel.getCategoryByTimeRange(startTime, endTime)
    }

    private fun initListener() {
        observePieChartData()
        binding.flPreTime.setOnClickListener {
            updateStartAndEndTime(false)
            initData()
        }
        binding.flNextTime.setOnClickListener {
            updateStartAndEndTime(true)
            initData()
        }
    }

    private fun updateStartAndEndTime(isNextTime: Boolean) {
        if (isNextTime) {
            startTime = nextMonthStartTime(startTime)
            endTime = monthEndTime(startTime)
        } else {
            startTime = preMonthStartTime(startTime)
            endTime = monthEndTime(startTime)
        }
        logd("xcx", "Category: ${getDateAndTime(startTime)}")
        logd("xcx", "Category: ${getDateAndTime(endTime)}")
        val arr = getMonthAndYear(startTime)
        if (startTime in yearStartTime()..yearEndTime()) {
            val text = "${arr[1]}月"
            binding.tvCurTime.text = text
        } else {
            binding.tvCurTime.text = format.format(startTime)
        }
    }

    private fun observePieChartData() {
        categoryModel.categoryInfo.observe(viewLifecycleOwner) { list ->
            val pieList = ChartHelper.handlePieChartData(list)
            ChartHelper.setPieChartData(binding.pcPayCount, pieList)
            val money = "-¥${getMoneyWithTwoDecimal(ChartHelper.pieChartTotalMoney)}"
            binding.tvPayCount.text = money
            val adapterList = mutableListOf<PayCountBean>()
            for (i in pieList.indices) {
                val bean = PayCountBean(
                    pieList[i].payCategory,
                    pieList[i].payMoney,
                    ChartHelper.pieChartTotalMoney,
                    startTime,
                    endTime,
                    ChartHelper.categoryColors[i % ChartHelper.categoryColors.size],
                    0
                )
                adapterList.add(bean)
            }
            adapterList.sortByDescending {
                it.payMoney
            }
            setDataToRecyclerView(adapterList)
        }

    }

    private fun setDataToRecyclerView(list: MutableList<PayCountBean>) {
        if (categoryAdapter == null) {
            binding.rvPayCountDetail.layoutManager = LinearLayoutManager(context)
            categoryAdapter = PayCountAdapter(payList = list)
            binding.rvPayCountDetail.adapter = categoryAdapter
            categoryAdapter?.setOnItemClickListener {
                startPayListActivity(
                    SHOW_DETAIL_CATEGORY_LIST,
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
