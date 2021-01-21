package com.xcx.account.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.xcx.account.AccountApp
import com.xcx.account.databinding.FragmentMyBinding
import com.xcx.account.repository.database.database.PayDataBase
import com.xcx.account.repository.database.database.PayDataBaseHelper
import com.xcx.account.utils.logd
import com.xcx.account.utils.loge
import com.xcx.account.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.*
import java.text.SimpleDateFormat
import kotlin.system.exitProcess


class MyFragment : BaseFragment() {

    private val TAG = "MyFragment"
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private val PERMISSION_REQUEST_CODE = 10010

    val CREATE_FILE = 100
    val PICK_FILE = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
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

//        checkAppPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val uri =
            Uri.parse("content://com.android.externalstorage.documents/document/primary:Account")
        val format = SimpleDateFormat("yyyy-MM-dd_HH_mm_ss")
        val fileName = "${format.format(System.currentTimeMillis())}.db"

        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_TITLE, fileName)

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
        }
        startActivityForResult(intent, CREATE_FILE)
    }

    private fun restoreLocalPayInfo() {
//        checkAppPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        val uri =
            Uri.parse("content://com.android.externalstorage.documents/document/primary:Account")

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"

            // Optionally, specify a URI for the file that should appear in the
            // system file picker when it loads.
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri)
        }

        startActivityForResult(intent, PICK_FILE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CREATE_FILE -> {
                    resultData?.data?.also { uri ->
                        createFile(uri)
                    }
                }
                PICK_FILE -> {
                    resultData?.data?.also { uri ->
                        copyFile(uri)
                    }
                }
            }
        }
    }

    private fun createFile(uri: Uri) {

        MainScope().launch {
            val job = launch(Dispatchers.IO) {
                PayDataBaseHelper.db.close()
                val file = AccountApp.appContext.getDatabasePath("pay-info.db")
                var out: OutputStream? = null
                try {
                    out = AccountApp.appContext.contentResolver.openOutputStream(uri)
                    out?.use {
                        it.write(file.readBytes())
                        it.flush()
                    }
                    logd(TAG, "createFile: ")
                } catch (e: Exception) {
                    loge(TAG, "createFile: ", e)
                }
            }
            job.join()
            loge(TAG, "createFile: success")
            exitProcess(0)
        }

    }

    private fun copyFile(uri: Uri) {
        MainScope().launch {
            val job = launch(Dispatchers.IO) {
                PayDataBaseHelper.db.close()
                var out: FileOutputStream? = null
                var input: InputStream? = null
                try {
                    val file = AccountApp.appContext.getDatabasePath("pay-info.db")
                    if (file.exists()) {
                        file.delete()
                    }
                    file.createNewFile()
                    logd(TAG, "createNewFile: ")
                    out = FileOutputStream(file)
                    input = AccountApp.appContext.contentResolver.openInputStream(uri)
                    out.use {
                        val arr = input?.use {ips->
                            ips.readBytes()
                        }
                        it.write(arr)
                        it.flush()
                    }
                } catch (e: Exception) {
                    loge(TAG, "createFile: ", e)
                }
            }
            job.join()
            loge(TAG, "copyFile: success")
            exitProcess(0)
        }
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