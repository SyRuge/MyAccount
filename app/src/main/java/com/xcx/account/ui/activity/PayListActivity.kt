package com.xcx.account.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcx.account.adapter.PayListAdapter
import com.xcx.account.databinding.ActivityPayListBinding
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.ui.dialog.showCommonDialog
import com.xcx.account.utils.logd
import com.xcx.account.utils.showToast
import com.xcx.account.viewmodel.PayListViewModel

class PayListActivity : BaseActivity() {

    private val TAG = "PayListActivity"
    lateinit var binding: ActivityPayListBinding

    val payListModel: PayListViewModel by viewModels()
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

    }

    private fun initListener() {
        binding.ivPaySortCategory.setOnClickListener {
            showCategoryDialog()
        }

        binding.ivPaySort.setOnClickListener {
            showSortDialog()
        }

        payListModel.payInfo.observe(this) {
            if (listAdapter == null) {
                it.sortByDescending { bean ->
                    bean.payTime
                }
                listAdapter = PayListAdapter(payList = it)
                binding.rvPayList.layoutManager = LinearLayoutManager(this)
                binding.rvPayList.adapter = listAdapter
                listAdapter?.setOnItemClickListener {
                    logd("PayDetailFragment", "id: ${it.id}")
                    startActivity(Intent(this, PayDetailActivity::class.java).apply {
                        putExtra("pay_id", it.id)
                    })
                }
                listAdapter?.setOnItemDeleteListener { bean ->
                    deletePayInfo(bean)
                }
            } else {
                it.sortByDescending { bean ->
                    bean.payTime

                }
                listAdapter?.updatePayInfoData(it)
            }
        }
        payListModel.deletePayInfo.observe(this) {
            if (it > 0) {
                showToast("删除成功!")
            }
        }

        payListModel.categoryPayInfo.observe(this) {
            it.sortByDescending { bean ->
                bean.payTime
            }
            listAdapter?.updatePayInfoData(it)
        }

    }

    private fun deletePayInfo(bean: PayInfoBean) {
        showCommonDialog(this, "删除交易", "确认要删除本条交易吗?") {
            onConfirm {
                payListModel.deletePayInfoById(PayInfoBean(bean.id, "", "", 0L, "", 0L, "", ""))
            }
        }
    }

    private fun showCategoryDialog() {
        //餐饮 水果 房租 通讯 购物 日用 水电 交通 全部

        val list = arrayOf("餐饮", "水果", "房租", "通讯", "购物", "日用", "水电", "交通")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        AlertDialog.Builder(this)
            .setTitle("分类")
            .setAdapter(
                adapter
            ) { dialog, which ->
                dialog.dismiss()
                payListModel.getPayInfoByCategory(list[which])
            }
            .create()
            .show()

    }

    private fun showSortDialog() {

        val list = arrayOf("按日期降序", "按日期升序", "按金额降序", "按金额升序")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        AlertDialog.Builder(this)
            .setTitle("分类")
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
