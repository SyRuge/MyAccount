package com.xcx.account.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.xcx.account.databinding.ActivityInputPayMoneyBinding
import com.xcx.account.utils.INPUT_MONEY_KEY
import com.xcx.account.utils.MODIFY_MONEY_ACTION
import com.xcx.account.utils.MODIFY_MONEY_KEY
import com.xcx.account.utils.getMoneyWithTwoDecimal

class InputPayMoneyActivity : BaseActivity() {

    lateinit var binding: ActivityInputPayMoneyBinding
    private var startAction = ""

    override fun getContentView(): View {
        binding = ActivityInputPayMoneyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun afterSetContentView(savedInstanceState: Bundle?) {
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        startAction = intent.action ?: ""
        val money = intent.getLongExtra(MODIFY_MONEY_KEY, 0)
        if (money > 0) {
            binding.tvMoney.text = getMoneyWithTwoDecimal(money)
        } else {
            binding.tvMoney.text = "0"
        }
    }

    private fun initData() {

    }

    private fun initListener() {
        binding.viewNumberBg.setOnClickListener {
            finish()
        }
        binding.tvNumber1.setOnClickListener {
            updateMoneyText("1")
        }
        binding.tvNumber2.setOnClickListener {
            updateMoneyText("2")
        }
        binding.tvNumber3.setOnClickListener {
            updateMoneyText("3")
        }
        binding.tvNumber4.setOnClickListener {
            updateMoneyText("4")
        }
        binding.tvNumber5.setOnClickListener {
            updateMoneyText("5")
        }
        binding.tvNumber6.setOnClickListener {
            updateMoneyText("6")
        }
        binding.tvNumber7.setOnClickListener {
            updateMoneyText("7")
        }
        binding.tvNumber8.setOnClickListener {
            updateMoneyText("8")
        }
        binding.tvNumber9.setOnClickListener {
            updateMoneyText("9")
        }
        binding.tvNumber0.setOnClickListener {
            updateMoneyText("0")
        }
        binding.tvNumberPoint.setOnClickListener {
            updateMoneyText(".")
        }
        binding.tvNumberDelete.setOnClickListener {
            deleteMoneyText()
        }
        binding.tvNumberClear.setOnClickListener {
            binding.tvMoney.text = "0"
        }
        binding.tvNumberReturn.setOnClickListener {
            finish()
        }
        binding.tvNumberOk.setOnClickListener {
            if (startAction == MODIFY_MONEY_ACTION) {
                setResult(RESULT_OK, Intent().apply {
                    putExtra(INPUT_MONEY_KEY, binding.tvMoney.text.toString())
                })
            } else {
                startActivity(Intent(this, AddPayInfoActivity::class.java).apply {
                    putExtra(INPUT_MONEY_KEY, binding.tvMoney.text.toString())
                })
            }
            finish()
        }
    }

    override fun isNeedSetStatusBarMode(): Boolean {
        return false
    }

    private fun updateMoneyText(number: String) {
        val oldMoney = binding.tvMoney.text.toString()
        //only one point
        if (oldMoney.contains(".") && number == ".") {
            return
        }
        //初始值为0,重复点击0
        if (oldMoney == "0" && number == "0") {
            return
        }
        //默认值0，点击其他数字，修改为点击的数字
        if (oldMoney == "0" && number != ".") {
            binding.tvMoney.text = number
            return
        }
        val new = oldMoney + number
//        binding.tvMoney.text = "$oldMoney$number"
        binding.tvMoney.text = new
    }

    private fun deleteMoneyText() {
        val oldMoney = binding.tvMoney.text.toString()
        //默认值0, 不删除
        if (oldMoney == "0") {
            return
        }
        if (oldMoney.length == 1 && oldMoney != "0") {
            binding.tvMoney.text = "0"
            return
        }
        val new = oldMoney.substring(0, oldMoney.length - 1)
        binding.tvMoney.text = new
    }
}
