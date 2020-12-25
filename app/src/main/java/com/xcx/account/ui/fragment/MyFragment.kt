package com.xcx.account.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.xcx.account.R
import com.xcx.account.utils.showToast

class MyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_set_1).setOnClickListener {
            showToast("set 1!!!")
        }
        view.findViewById<Button>(R.id.btn_set_0).setOnClickListener {
            showToast("set 0!!!")
        }
    }
}