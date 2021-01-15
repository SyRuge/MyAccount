package com.xcx.account.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcx.account.adapter.PayListAdapter
import com.xcx.account.databinding.ActivityPayListBinding
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.ui.dialog.showCommonDialog
import com.xcx.account.utils.logd
import com.xcx.account.utils.showToast
import com.xcx.account.viewmodel.PayListViewModel

class PayListActivity : BaseActivity() {

    lateinit var binding: ActivityPayListBinding

    val payListModel: PayListViewModel by viewModels()
    var listAdapter: PayListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initListener()
    }

    private fun initData() {

    }

    private fun initListener() {
        payListModel.payInfo.observe(this) {
            if (listAdapter == null) {
                val listAdapter = PayListAdapter(payList = it)
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
                listAdapter?.updatePayInfoData(it)
            }
        }
        payListModel.deletePayInfo.observe(this) {
            if (it > 0) {
                showToast("删除成功!")
            }
        }

    }

    private fun deletePayInfo(bean: PayInfoBean) {
        showCommonDialog(this, "删除交易", "确认要删除本条交易吗?") {
            onConfirm {
                payListModel.deletePayInfoById(PayInfoBean(bean.id, "", "", 0L, "", 0L, "", ""))
            }
        }
    }
}
