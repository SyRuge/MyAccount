package com.xcx.account.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcx.account.R
import com.xcx.account.adapter.TodayPayAdapter
import com.xcx.account.adapter.TotalPayAdapter
import com.xcx.account.bean.HomePayBean
import com.xcx.account.bean.HomeTotalPayBean
import com.xcx.account.databinding.FragmentHomeBinding
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.ui.activity.InputPayMoneyActivity
import com.xcx.account.ui.activity.PayDetailActivity
import com.xcx.account.ui.activity.PayListActivity
import com.xcx.account.ui.dialog.showCommonDialog
import com.xcx.account.utils.*
import com.xcx.account.viewmodel.PayViewModel

class HomeFragment : BaseFragment() {

    private var TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var payAdapter: TodayPayAdapter? = null
    private var totalAdapter: TotalPayAdapter? = null
    private val totalPayList = mutableListOf<HomeTotalPayBean>()

    private val payModel: PayViewModel by lazy {
        ViewModelProvider(this).get(PayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        binding.tvToolbarTitle.text = getString(R.string.tab_home)
        binding.srlRefreshPayInfo.setColorSchemeResources(R.color.colorAccent)
    }

    private fun initData() {

    }

    private fun initListener() {
        binding.srlRefreshPayInfo.setOnRefreshListener {
            binding.srlRefreshPayInfo.run {
                postDelayed({ isRefreshing = false }, 1500)
            }
        }

        binding.fabAddPay.setOnClickListener {
            startActivity(Intent(activity, InputPayMoneyActivity::class.java))
        }

        payModel.allPayInfo.observe(viewLifecycleOwner) { oriList ->
            //update today pay info
            updateTodayPayUi(oriList)
            //update total pay info
            updateTotalPayUi(oriList)

        }
        payModel.deletePayInfo.observe(viewLifecycleOwner) {
            if (it > 0) {
                showToast(getString(R.string.delete_pay_success))
            }
        }
    }

    private fun updateTodayPayUi(oriList: List<HomePayBean>) {
        val list = oriList.filter {
            it.payTime in todayStartTime()..todayEndTime()
        }.sortedByDescending {
            it.payTime
        }.toMutableList()

        if (payAdapter == null) {
            binding.rvPayContent.layoutManager = LinearLayoutManager(activity)
            payAdapter = TodayPayAdapter(payList = list)
            binding.rvPayContent.adapter = payAdapter
            payAdapter?.setOnItemClickListener {
                logd(TAG, "id: ${it.id}")
                startActivity(Intent(activity, PayDetailActivity::class.java).apply {
                    putExtra(PAY_ID_KEY, it.id)
                })
            }
            payAdapter?.setOnItemDeleteListener { bean ->
                showCommonDialog(context,
                    getString(R.string.dialog_delete_pay_title),
                    getString(R.string.dialog_delete_pay_msg)) {
                    onConfirm {
                        payModel.deletePayInfoById(PayInfoBean(bean.id, "", "", 0L, "", 0L, "", ""))
                    }
                }
            }
        } else {
            payAdapter?.updatePayInfoData(list)
        }
    }

    /**
     * sum total pay
     */
    private fun updateTotalPayUi(list: List<HomePayBean>) {
        val totalPayMoney = getTotalPayMoney(list)
        if (totalAdapter == null) {
            totalPayList.add(HomeTotalPayBean(getString(R.string.cur_day_dec), todayDate(), totalPayMoney[0], "", ""))
            totalPayList.add(HomeTotalPayBean(getString(R.string.cur_week_dec), curWeekRangeDate(), totalPayMoney[1], "", ""))
            totalPayList.add(HomeTotalPayBean(getString(R.string.cur_month_dec), curMonthRangeDate(), totalPayMoney[2], "", ""))
            totalPayList.add(HomeTotalPayBean(getString(R.string.cur_year_dec), curRangeYearDate(), totalPayMoney[3], "", ""))

            binding.rvTotalPayContent.layoutManager = LinearLayoutManager(activity)
            totalAdapter = TotalPayAdapter(payList = totalPayList)
            binding.rvTotalPayContent.adapter = totalAdapter
            totalAdapter?.setOnItemClickListener {_, position->
                when(position) {
                    1 -> {
                        startPayListActivity(SHOW_TIME_RANGE_LIST, todayStartTime(), todayEndTime())
                    }
                    2 -> {
                        startPayListActivity(SHOW_TIME_RANGE_LIST, weekStartTime(), weekEndTime())
                    }
                    3->{
                        startPayListActivity(SHOW_TIME_RANGE_LIST, monthStartTime(), monthEndTime())
                    }
                    4->{
                        startPayListActivity(SHOW_TIME_RANGE_LIST, yearStartTime(), yearEndTime())
                    }
                }
            }
        } else {

            totalAdapter?.apply {
                totalPayList[0].payRangeDate = todayDate()
                totalPayList[0].payMoney = totalPayMoney[0]

                totalPayList[1].payRangeDate = curWeekRangeDate()
                totalPayList[1].payMoney = totalPayMoney[1]

                totalPayList[2].payRangeDate = curMonthRangeDate()
                totalPayList[2].payMoney = totalPayMoney[2]

                totalPayList[3].payRangeDate = curRangeYearDate()
                totalPayList[3].payMoney = totalPayMoney[3]
                notifyDataSetChanged()
            }
        }

    }

    private fun getTotalPayMoney(list: List<HomePayBean>): Array<Long> {
        var todayTotalPay = 0L
        var weekTotalPay = 0L
        var monthTotalPay = 0L
        var yearTotalPay = 0L

        list.forEach {
            //1. 计算本日支出
            if (it.payTime in todayStartTime()..todayEndTime()) {
                todayTotalPay += it.payMoney
            }
            //2. 计算本周支出
            if (it.payTime in weekStartTime()..weekEndTime()) {
                weekTotalPay += it.payMoney
            }
            //3. 计算本月支出
            if (it.payTime in monthStartTime()..monthEndTime()) {
                monthTotalPay += it.payMoney
            }
            //4. 计算本年支出
            if (it.payTime in yearStartTime()..yearEndTime()) {
                yearTotalPay += it.payMoney
            }
        }

        return arrayOf(todayTotalPay, weekTotalPay, monthTotalPay, yearTotalPay)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}