package com.xcx.account.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.xcx.account.databinding.DialogChangePayNoteBinding
import com.xcx.account.utils.PAY_NOTE_KEY
import com.xcx.account.utils.dp2px


/**
 * Created by SyRuge on 2021年01月05日.
 */
class ChangePayNoteDialog : DialogFragment() {

    private var _binding: DialogChangePayNoteBinding? = null
    private val binding get() = _binding!!
    private var payNote = ""
    private var listener: DialogClickListener? = null

    fun setDialogClickListener(listener: DialogClickListener.() -> Unit) {
        this.listener = DialogClickListener().also(listener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            payNote = getString(PAY_NOTE_KEY, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogChangePayNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
    }

    private fun initData() {
        dialog?.setCanceledOnTouchOutside(false)
        binding.etPayNote.setText(payNote)
    }

    private fun initListener() {
        binding.tvConfirm.setOnClickListener {
            listener?.confirm?.invoke(binding.etPayNote.text.toString())
            hideKeyBoard()
            dismiss()
        }
        binding.tvCancel.setOnClickListener {
            listener?.cancel?.invoke()
            hideKeyBoard()
            dismiss()
        }
    }

    private fun hideKeyBoard() {
        activity?.apply {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etPayNote.windowToken, 0)
        }
    }

    override fun onStart() {
        super.onStart()
        val params = dialog?.window?.attributes?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = dp2px(200)
        }
        dialog?.window?.attributes = params
    }

    override fun onResume() {
        super.onResume()
        binding.etPayNote.requestFocus()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    class DialogClickListener {
        var cancel: (() -> Unit)? = null
        var confirm: ((String) -> Unit)? = null

        fun onCancel(cancel: () -> Unit) {
            this.cancel = cancel
        }

        fun onConfirm(confirm: (String) -> Unit) {
            this.confirm = confirm
        }

    }
}