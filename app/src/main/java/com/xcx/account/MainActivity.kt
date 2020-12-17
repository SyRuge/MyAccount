package com.xcx.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.xcx.account.adapter.AccountAdapter
import com.xcx.account.databinding.ActivityMainBinding
import com.xcx.account.utils.logd

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

    }

    private fun initData() {
        binding.vpContent.adapter = AccountAdapter(this)
        TabLayoutMediator(
            binding.tlBottomTab, binding.vpContent
        ) { tab, position ->
            logd(TAG, "select position: $position")
        }
    }

    private fun initListener() {

    }
}