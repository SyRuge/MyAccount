package com.xcx.account.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.xcx.account.AccountApp
import com.xcx.account.R
import com.xcx.account.adapter.PayCategoryAdapter
import com.xcx.account.bean.PayCategoryBean
import com.xcx.account.databinding.ActivityAddPayInfoBinding
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.utils.INPUT_MONEY_KEY
import com.xcx.account.utils.logd
import com.xcx.account.utils.showToast
import com.xcx.account.viewmodel.AddPayInfoModel
import java.math.BigDecimal

class AddPayInfoActivity : BaseActivity() {

    val TAG = "AddPayInfoActivity"
    private var oriMoney = ""
    private var selectCategory = AccountApp.appContext.getString(R.string.category_food)

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
        oriMoney = intent.getStringExtra(INPUT_MONEY_KEY) ?: ""
        formatPayMoney()
        val list = getPayCategoryList()
        binding.rvPayCategory.layoutManager = GridLayoutManager(this, 3)
        val payCategoryAdapter = PayCategoryAdapter(categoryList = list)
        binding.rvPayCategory.adapter = payCategoryAdapter
        payCategoryAdapter.setOnItemClickListener { bean ->
            if (bean.payCategory == getString(R.string.category_all)) {
                showAllCategoryDialog()
            } else {
                selectCategory = bean.payCategory
            }
        }
    }

    private fun showAllCategoryDialog() {
        //餐饮 水果 房租 通讯 购物 日用 水电 交通

        val list = arrayOf(
            getString(R.string.category_snacks),
            getString(R.string.category_clothes),
            getString(R.string.category_rent))

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.category_dec))
            .setAdapter(
                adapter
            ) { dialog, which ->
                dialog.dismiss()
                selectCategory = list[which]
            }
            .create()
            .show()
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
        //餐饮 水果 娱乐 通讯 购物 日用 水电 交通 全部
        val list = mutableListOf<PayCategoryBean>().apply {
            add(PayCategoryBean(R.color.pink_color_500, getString(R.string.category_food), true))
            add(PayCategoryBean(R.color.primaryTextColor, getString(R.string.category_fruit)))
            add(PayCategoryBean(R.color.primaryTextColor, getString(R.string.category_fun)))
            add(PayCategoryBean(R.color.primaryTextColor, getString(R.string.category_comm)))
            add(PayCategoryBean(R.color.primaryTextColor, getString(R.string.category_shop)))
            add(PayCategoryBean(R.color.primaryTextColor,
                getString(R.string.category_daily_expenses)))
            add(PayCategoryBean(R.color.primaryTextColor, getString(R.string.category_utilities)))
            add(PayCategoryBean(R.color.primaryTextColor, getString(R.string.category_traffic)))
            add(PayCategoryBean(R.color.primaryTextColor, getString(R.string.category_all)))
        }
        return list
    }
}
