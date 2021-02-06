package com.xcx.account.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcx.account.R
import com.xcx.account.adapter.PayListAdapter
import com.xcx.account.databinding.ActivityPayListBinding
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.ui.dialog.showCommonDialog
import com.xcx.account.utils.*
import com.xcx.account.viewmodel.PayListViewModel

class PayListActivity : BaseActivity() {

    private val TAG = "PayListActivity"
    lateinit var binding: ActivityPayListBinding

    private var listType = SHOW_CUR_YEAR_LIST
    private var startTime = 0L
    private var endTime = 0L
    private var payCategory = ""

    private val payListModel: PayListViewModel by viewModels()
    var listAdapter: PayListAdapter? = null

    override fun getContentView(): View {
        binding = ActivityPayListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        return binding.root
    }

    override fun afterSetContentView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.tlToolbar)
        initData()
        initListener()
    }

    private fun initData() {
        intent.apply {
            listType = getIntExtra(PAY_LIST_TYPE_KEY, SHOW_CUR_YEAR_LIST)
            startTime = getLongExtra(PAY_LIST_START_TIME_KEY, yearStartTime())
            endTime = getLongExtra(PAY_LIST_END_TIME_KEY, yearEndTime())
            payCategory = getStringExtra(PAY_LIST_CATEGORY_KEY) ?: getString(R.string.category_food)
        }
        when (listType) {
            SHOW_CUR_YEAR_LIST -> {
                binding.ivPaySortCategory.visibility = View.VISIBLE
                payListModel.getPayInfoByTimeRange(yearStartTime(), yearEndTime())
            }
            SHOW_TIME_RANGE_LIST -> {
                binding.ivPaySortCategory.visibility = View.VISIBLE
                payListModel.getPayInfoByTimeRange(startTime, endTime)
            }
            SHOW_DETAIL_CATEGORY_LIST -> {
                binding.ivPaySortCategory.visibility = View.GONE
                payListModel.getPayInfoByCategoryAndTime(payCategory, startTime, endTime)
            }
            SHOW_DETAIL_TIME_RANGE_LIST -> {
                binding.ivPaySortCategory.visibility = View.GONE
                payListModel.getPayInfoByTimeRange(startTime, endTime)
            }
        }
    }

    private fun initListener() {
        binding.ivPaySortCategory.setOnClickListener {
            showCategoryDialog()
        }

        binding.ivPaySort.setOnClickListener {
            showSortDialog()
        }

        payListModel.categoryPayInfo.observe(this) {
            updatePayList(it)
        }

        payListModel.timeRangePayInfo.observe(this) {
            updatePayList(it)
        }

        payListModel.deletePayInfo.observe(this) {
            if (it > 0) {
                showToast(getString(R.string.delete_pay_success))
            }
        }
    }

    private fun updatePayList(list: MutableList<PayInfoBean>) {
        if (listAdapter == null) {
            list.sortByDescending { bean ->
                bean.payTime
            }
            listAdapter = PayListAdapter(payList = list)
            binding.rvPayList.layoutManager = LinearLayoutManager(this)
            binding.rvPayList.adapter = listAdapter
            listAdapter?.setOnItemClickListener {
                logd(TAG, "id: ${it.id}")
                startActivity(Intent(this, PayDetailActivity::class.java).apply {
                    putExtra(PAY_ID_KEY, it.id)
                })
            }
            listAdapter?.setOnItemDeleteListener { bean ->
                deletePayInfo(bean)
            }
        } else {
            list.sortByDescending { bean ->
                bean.payTime
            }
            listAdapter?.updatePayInfoData(list)
        }
    }

    private fun deletePayInfo(bean: PayInfoBean) {
        showCommonDialog(
            this,
            getString(R.string.dialog_delete_pay_title),
            getString(R.string.dialog_delete_pay_msg)
        ) {
            onConfirm {
                payListModel.deletePayInfoById(PayInfoBean(bean.id, "", "", 0L, "", 0L, "", ""))
            }
        }
    }

    private fun showCategoryDialog() {
        //餐饮 水果 房租 通讯 购物 日用 水电 交通

        val list = arrayOf(
            getString(R.string.category_food),
            getString(R.string.category_fruit),
            getString(R.string.category_fun),
            getString(R.string.category_comm),
            getString(R.string.category_shop),
            getString(R.string.category_daily_expenses),
            getString(R.string.category_utilities),
            getString(R.string.category_traffic),
            getString(R.string.category_snacks),
            getString(R.string.category_clothes),
            getString(R.string.category_rent)
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.category_dec))
            .setAdapter(
                adapter
            ) { dialog, which ->
                dialog.dismiss()
                payListModel.getPayInfoByCategoryAndTime(list[which], startTime, endTime)
            }
            .create()
            .show()

    }

    private fun showSortDialog() {

        val list = arrayOf(
            getString(R.string.sort_by_date_desc),
            getString(R.string.sort_by_date_asc),
            getString(
                R.string.sort_by_money_desc
            ),
            getString(R.string.sort_by_money_asc)
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.sort_dec))
            .setAdapter(
                adapter
            ) { dialog, which ->
                dialog.dismiss()
                sortPayInfo(which)
            }
            .create()
            .show()

    }

    private fun sortPayInfo(which: Int) {
        when (which) {
            0 -> {
                listAdapter?.payList?.sortByDescending {
                    it.payTime
                }
                listAdapter?.notifyDataSetChanged()
            }
            1 -> {
                listAdapter?.payList?.sortBy {
                    it.payTime
                }
                listAdapter?.notifyDataSetChanged()
            }
            2 -> {
                listAdapter?.payList?.sortByDescending {
                    it.payMoney
                }
                listAdapter?.notifyDataSetChanged()
            }
            3 -> {
                listAdapter?.payList?.sortBy {
                    it.payMoney
                }
                listAdapter?.notifyDataSetChanged()
            }
        }
    }
}
