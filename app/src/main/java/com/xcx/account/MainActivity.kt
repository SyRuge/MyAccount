package com.xcx.account

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.xcx.account.adapter.AccountAdapter
import com.xcx.account.databinding.ActivityMainBinding
import com.xcx.account.utils.logd
import com.xcx.account.utils.showToast

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        window.navigationBarColor = Color.parseColor("#fffafafa")
    }

    private fun initData() {
        binding.vpContent.adapter = AccountAdapter(this)
        TabLayoutMediator(
            binding.tlBottomTab, binding.vpContent,true,true
        ) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_home)
                1 -> tab.text = getString(R.string.tab_count)
                2 -> tab.text = getString(R.string.tab_my)
            }
            logd(TAG, "select position: $position")
//            showToast(position.toString())
        }.attach()
    }

    private fun initListener() {
        /*binding.fabAddPay.setOnClickListener {
            showToast("fab click")
        }*/
    }
}