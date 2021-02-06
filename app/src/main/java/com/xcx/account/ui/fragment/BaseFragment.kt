package com.xcx.account.ui.fragment

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment

/**
 * Created by SyRuge on 2021年01月15日.
 */
abstract class BaseFragment : Fragment() {
    lateinit var context: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context as Activity
    }
}