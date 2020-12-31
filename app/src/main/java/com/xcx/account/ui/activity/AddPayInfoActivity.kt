package com.xcx.account.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xcx.account.R
import com.xcx.account.ui.fragment.AddPayInfoFragment

class AddPayInfoActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pay_info)
        initData()
        initListener()
    }

    private fun initData() {
        val money = intent.getStringExtra("input_money")
        supportFragmentManager.beginTransaction()
            .add(R.id.cl_root, AddPayInfoFragment().apply {
                arguments = Bundle().apply {
                    putString("input_money", money)
                }
            }).commitNowAllowingStateLoss()
    }

    private fun initListener() {

    }
}
