package com.xcx.account.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xcx.account.databinding.FragmentMyBinding

class MyFragment : Fragment() {

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
    }

    private fun initData() {

    }

    private fun initListener() {
        binding.llPayBackup.setOnClickListener {
            backUpPayInfoToLocal()
        }
        binding.llPayRestore.setOnClickListener {
            restoreLocalPayInfo()
        }
    }

    private fun restoreLocalPayInfo() {

    }

    private fun backUpPayInfoToLocal() {
        TODO()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}