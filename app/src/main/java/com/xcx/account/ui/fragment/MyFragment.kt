package com.xcx.account.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.xcx.account.AccountApp
import com.xcx.account.databinding.FragmentMyBinding
import com.xcx.account.utils.showToast


class MyFragment : BaseFragment() {

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private val PERMISSION_REQUEST_CODE = 10010
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

    private fun backUpPayInfoToLocal() {
        checkAppPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    }

    private fun restoreLocalPayInfo() {
        checkAppPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun checkAppPermission(permission: String) {
        val isGranted = ContextCompat.checkSelfPermission(
            AccountApp.appContext,
            permission
        )
        if (isGranted == PackageManager.PERMISSION_GRANTED) {
            showToast("PERMISSION_GRANTED")
        } else {
            requestPermissions(arrayOf(permission), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showToast("backup or restore click")
                } else {
                    showToast("PERMISSION_DENIED")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}