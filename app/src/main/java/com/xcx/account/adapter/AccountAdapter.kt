package com.xcx.account.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xcx.account.ui.fragment.CountFragment
import com.xcx.account.ui.fragment.HomeFragment
import com.xcx.account.ui.fragment.MyFragment

/**
 * Created by SyRuge on 2020年12月17日.
 */
class AccountAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int) = when (position) {
        0 -> HomeFragment()
        1 -> CountFragment()
        2 -> MyFragment()
        else -> HomeFragment()
    }
}