package com.xcx.account.ui.activity

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.xcx.account.R
import com.xcx.account.adapter.AccountAdapter
import com.xcx.account.databinding.ActivityMainBinding
import com.xcx.account.utils.logd

class MainActivity : BaseActivity() {

    val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding

    override fun getContentView(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        return binding.root
    }

    override fun afterSetContentView(savedInstanceState: Bundle?) {
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        window.navigationBarColor = Color.parseColor("#FFFFFF")
        binding.tlBottomTab.apply {
            addTab(newTab())
            addTab(newTab())
            addTab(newTab())
        }
    }

    private fun initData() {
        binding.vpContent.adapter = AccountAdapter(this)
        binding.tlBottomTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                updateTabText(tab, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                updateTabText(tab, false)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }

        })
        TabLayoutMediator(
            binding.tlBottomTab, binding.vpContent, true, true
        ) { tab, position ->
            when (position) {
                0 -> {
                    val home =
                        layoutInflater.inflate(R.layout.ty_tab_item, binding.tlBottomTab, false)
                    val ivTabIcon: ImageView = home.findViewById(R.id.iv_tab_icon)
                    val tvTabText: TextView = home.findViewById(R.id.tv_tab_text)
                    tab.customView = home
                    ivTabIcon.setBackgroundResource(R.drawable.tab_home_selector)
                    tvTabText.text = getString(R.string.tab_home)
                }
                1 -> {
                    val count =
                        layoutInflater.inflate(R.layout.ty_tab_item, binding.tlBottomTab, false)
                    val ivTabIcon: ImageView = count.findViewById(R.id.iv_tab_icon)
                    val tvTabText: TextView = count.findViewById(R.id.tv_tab_text)
                    tab.customView = count
                    ivTabIcon.setBackgroundResource(R.drawable.tab_count_selector)
                    tvTabText.text = getString(R.string.tab_count)
                }
                2 -> {
                    val my =
                        layoutInflater.inflate(R.layout.ty_tab_item, binding.tlBottomTab, false)
                    val ivTabIcon: ImageView = my.findViewById(R.id.iv_tab_icon)
                    val tvTabText: TextView = my.findViewById(R.id.tv_tab_text)
                    tab.customView = my
                    ivTabIcon.setBackgroundResource(R.drawable.tab_my_selector)
                    tvTabText.text = getString(R.string.tab_my)
                }
            }
            logd(TAG, "select position: $position")
//            showToast(position.toString())
        }.attach()
    }

    private fun initListener() {

    }

    private fun updateTabText(tab: TabLayout.Tab, isSelect: Boolean) {
        if (isSelect) {
            tab.customView?.apply {
                val tv: TextView = findViewById(R.id.tv_tab_text)
                tv.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            }
        } else {
            tab.customView?.apply {
                val tv: TextView = findViewById(R.id.tv_tab_text)
                tv.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
            }
        }
    }
}