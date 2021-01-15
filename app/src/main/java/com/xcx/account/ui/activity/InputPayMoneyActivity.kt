package com.xcx.account.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xcx.account.databinding.ActivityInputPayMoneyBinding

class InputPayMoneyActivity : BaseActivity() {

    lateinit var binding: ActivityInputPayMoneyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputPayMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
        initListener()
    }

    private fun initView() {

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
            updateMoneyText("6")
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
            startActivity(Intent(this, AddPayInfoActivity::class.java).apply {
                putExtra("input_money", binding.tvMoney.text.toString())
            })
            finish()
        }
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
