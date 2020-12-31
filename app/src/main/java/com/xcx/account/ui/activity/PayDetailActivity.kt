package com.xcx.account.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xcx.account.R
import com.xcx.account.ui.fragment.PayDetailFragment

class PayDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_detail)
        initData()
    }

    private fun initData() {
        val id = intent.getLongExtra("pay_id", 0)
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_detail_content, PayDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong("pay_id", id)
                }
            })
            .commitNowAllowingStateLoss()
    }
}
