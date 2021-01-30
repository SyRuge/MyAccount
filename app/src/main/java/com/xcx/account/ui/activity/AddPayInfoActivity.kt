package com.xcx.account.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.xcx.account.R
import com.xcx.account.adapter.PayCategoryAdapter
import com.xcx.account.bean.PayCategoryBean
import com.xcx.account.databinding.ActivityAddPayInfoBinding
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.utils.logd
import com.xcx.account.utils.showToast
import com.xcx.account.viewmodel.AddPayInfoModel
import java.math.BigDecimal

class AddPayInfoActivity : BaseActivity() {

    val TAG = "AddPayInfoActivity"
    private var oriMoney = ""
    private val KEY_INPUT_MONEY = "input_money"
    private var selectCategory = "餐饮"

    lateinit var binding: ActivityAddPayInfoBinding
    private val addPayModel: AddPayInfoModel by viewModels()


    override fun getContentView(): View {
        binding = ActivityAddPayInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        return binding.root
    }

    override fun afterSetContentView(savedInstanceState: Bundle?) {
        super.afterSetContentView(savedInstanceState)
        setSupportActionBar(binding.tlToolbar)
        initData()
        initListener()
    }

    private fun initData() {
        oriMoney = intent.getStringExtra(KEY_INPUT_MONEY) ?: ""
        formatPayMoney()
        val list = getPayCategoryList()
        binding.rvPayCategory.layoutManager = GridLayoutManager(this, 3)
        val payCategoryAdapter = PayCategoryAdapter(categoryList = list)
        binding.rvPayCategory.adapter = payCategoryAdapter
        payCategoryAdapter.setOnItemClickListener { bean ->
            selectCategory = bean.payCategory
        }
    }

    private fun initListener() {
        binding.btnAddPayInfo.setOnClickListener {
            if (oriMoney.isEmpty()) {
                return@setOnClickListener
            }
            val ori = BigDecimal(oriMoney)
            val money = ori.multiply(BigDecimal.valueOf(100)).toLong()
            val note = binding.etPayNote.text.toString()
            val bean = PayInfoBean(
                0,
                "0",
                selectCategory,
                money,
                selectCategory,
                System.currentTimeMillis(),
                "",
                note
            )
            addPayModel.addPayInfo(bean)
        }
        addPayModel.addPayInfo.observe(this) {
            if (it > 0) {
                showToast(getString(R.string.add_pay_info_success))

            }
            finish()
        }
    }

    private fun formatPayMoney() {
        logd(TAG, "formatPayMoney oriMoney: $oriMoney")
        if (oriMoney.isNotEmpty()) {
            val ori = BigDecimal(oriMoney)
            val b1 = ori.multiply(BigDecimal.valueOf(100L))

            val b2 = b1.divide(BigDecimal.valueOf(100.0)).setScale(2, BigDecimal.ROUND_DOWN)
            val money = "-¥$b2"
            binding.tvPayMoney.text = money
        }
    }

    private fun getPayCategoryList(): MutableList<PayCategoryBean> {
        //餐饮 水果 房租 通讯 购物 日用 水电 交通 全部
        val list = mutableListOf<PayCategoryBean>()
        list.add(PayCategoryBean(R.color.pink_color_500, "餐饮", true))
        list.add(PayCategoryBean(R.color.primaryTextColor, "水果"))
        list.add(PayCategoryBean(R.color.primaryTextColor, "房租"))
        list.add(PayCategoryBean(R.color.primaryTextColor, "通讯"))
        list.add(PayCategoryBean(R.color.primaryTextColor, "购物"))
        list.add(PayCategoryBean(R.color.primaryTextColor, "日用"))
        list.add(PayCategoryBean(R.color.primaryTextColor, "水电"))
        list.add(PayCategoryBean(R.color.primaryTextColor, "交通"))
        list.add(PayCategoryBean(R.color.primaryTextColor, "全部"))
        return list
    }
}
