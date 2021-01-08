package com.xcx.account.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.xcx.account.adapter.PayCategoryAdapter
import com.xcx.account.bean.PayCategoryBean
import com.xcx.account.databinding.ActivityAddPayInfoBinding
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.utils.logd
import com.xcx.account.utils.showToast
import kotlinx.coroutines.*
import java.math.BigDecimal

class AddPayInfoActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    val TAG = "AddPayInfoActivity"
    private var oriMoney = ""
    private val KEY_INPUT_MONEY = "input_money"
    private var selectCategory = "餐饮"

    lateinit var binding: ActivityAddPayInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPayInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initListener()
    }

    private fun initData() {
        oriMoney = intent.getStringExtra("input_money") ?: ""
        formatPayMoney()
        val list = getPayCategoryList()
        binding.rvPayCategory.layoutManager = GridLayoutManager(this, 3)
        val payCategoryAdapter = PayCategoryAdapter(categoryList = list)
        binding.rvPayCategory.adapter = payCategoryAdapter
        payCategoryAdapter.setOnItemClickListener(object : PayCategoryAdapter.OnItemClickListener {
            override fun onItemClick(bean: PayCategoryBean) {
                selectCategory = bean.payCategory
            }
        })
    }

    private fun initListener() {
        binding.btnAddPayInfo.setOnClickListener {
            if (oriMoney.isEmpty()) {
                return@setOnClickListener
            }
            val ori = BigDecimal(oriMoney)
            val money = ori.multiply(BigDecimal.valueOf(100)).toLong()
            val note = binding.etPayNote.text.toString()
            launch {
                val defer = async(Dispatchers.IO) {
                    PayRepository.addPayInfo(
                        PayInfoBean(
                            0,
                            "0",
                            selectCategory,
                            money,
                            selectCategory,
                            System.currentTimeMillis(),
                            "",
                            note
                        )
                    )
                }
                val row = defer.await()
                if (row > 0) {
                    showToast("add success!")
                }
                finish()
            }

        }
    }

    private fun formatPayMoney() {
        logd(TAG, "formatPayMoney oriMoney: $oriMoney")
        if (oriMoney.isNotEmpty()) {
            val ori = BigDecimal(oriMoney)
            val b1 = ori.multiply(BigDecimal.valueOf(100L))

            val b2 = b1.divide(BigDecimal.valueOf(100.0)).setScale(2, BigDecimal.ROUND_DOWN)
            val money = "-￥$b2"
            binding.tvPayMoney.text = money
        }
    }

    private fun getPayCategoryList(): MutableList<PayCategoryBean> {
        //餐饮 水果 房租 通讯 购物 日用 水电 交通 全部
        var list = mutableListOf<PayCategoryBean>()
        list.add(PayCategoryBean("餐饮", true))
        list.add(PayCategoryBean("水果"))
        list.add(PayCategoryBean("房租"))
        list.add(PayCategoryBean("通讯"))
        list.add(PayCategoryBean("购物"))
        list.add(PayCategoryBean("日用"))
        list.add(PayCategoryBean("水电"))
        list.add(PayCategoryBean("交通"))
        list.add(PayCategoryBean("全部"))
        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
