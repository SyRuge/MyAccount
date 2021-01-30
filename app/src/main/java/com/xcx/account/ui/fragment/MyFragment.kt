package com.xcx.account.ui.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.xcx.account.AccountApp
import com.xcx.account.R
import com.xcx.account.databinding.FragmentMyBinding
import com.xcx.account.utils.showToast
import com.xcx.account.viewmodel.MyViewModel
import java.text.SimpleDateFormat


class MyFragment : BaseFragment() {

    private val TAG = "MyFragment"
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private val PERMISSION_REQUEST_CODE = 10010
    private val URI_FILE_LOCATION =
        "content://com.android.externalstorage.documents/document/primary:Account"
    private val FILE_MIME_TYPE = "application/json"

    private val PICK_JSON_FILE = 101
    private val CREATE_JSON_FILE = 102
    private val myModel: MyViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        binding.tvToolbarTitle.text = getString(R.string.tab_my)
    }

    private fun initData() {

    }

    private fun initListener() {
        binding.llPayBackup.setOnClickListener {
            val format = SimpleDateFormat("yyyy-MM-dd_HH_mm_ss")
            val fileName = "${format.format(System.currentTimeMillis())}.json"
            val uri = Uri.parse(URI_FILE_LOCATION)
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = FILE_MIME_TYPE
                putExtra(Intent.EXTRA_TITLE, fileName)
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
            }
            startActivityForResult(intent, CREATE_JSON_FILE)
        }
        binding.llPayRestore.setOnClickListener {
            val uri = Uri.parse(URI_FILE_LOCATION)
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = FILE_MIME_TYPE
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
            }
            startActivityForResult(intent, PICK_JSON_FILE)
        }

        myModel.backupStatus.observe(viewLifecycleOwner) {
            if (it) {
                showToast(getString(R.string.back_up_success))
            } else {
                showToast(getString(R.string.back_up_error))
            }
        }

        myModel.restoreStatus.observe(viewLifecycleOwner) {
            if (it) {
                showToast(getString(R.string.restore_success))
            } else {
                showToast(getString(R.string.restore_error))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CREATE_JSON_FILE -> {
                    resultData?.data?.also { uri ->
                        myModel.backupPayInfoJsonToLocal(uri)
                    }
                }
                PICK_JSON_FILE -> {
                    resultData?.data?.also { uri ->
                        myModel.restorePayInfoJsonFromLocal(uri)
                    }
                }
            }
        }
    }

    @Deprecated("will remove next path")
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

    @Deprecated("will remove next path")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
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