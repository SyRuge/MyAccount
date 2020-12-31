package com.xcx.account.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.xcx.account.databinding.FragmentNumberBinding
import com.xcx.account.ui.activity.AddPayInfoActivity
import kotlinx.android.synthetic.main.fragment_number.view.*

/**
 * Created by xuchongxiang on 2020年12月28日.
 */
class MoneyNumberDialog : DialogFragment() {

    private var _binding: FragmentNumberBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        _binding = FragmentNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
    }

    private fun initListener() {
        binding.tvNumber1.setOnClickListener {
            updateMoneyText("1")
        }
        binding.tvNumber2.setOnClickListener {
            updateMoneyText("2")
        }
        binding.tvNumber3.setOnClickListener {
            updateMoneyText("3")
        }
        binding.tvNumber4.setOnClickListener {
            updateMoneyText("4")
        }
        binding.tvNumber5.setOnClickListener {
            updateMoneyText("6")
        }
        binding.tvNumber6.setOnClickListener {
            updateMoneyText("6")
        }
        binding.tvNumber7.setOnClickListener {
            updateMoneyText("7")
        }
        binding.tvNumber8.setOnClickListener {
            updateMoneyText("8")
        }
        binding.tvNumber9.setOnClickListener {
            updateMoneyText("9")
        }
        binding.tvNumber0.setOnClickListener {
            updateMoneyText("0")
        }
        binding.tvNumberPoint.setOnClickListener {
            updateMoneyText(".")
        }
        binding.tvNumberDelete.setOnClickListener {
            deleteMoneyText()
        }
        binding.tvNumberClear.setOnClickListener {
            binding.tvMoney.text = "0"
        }
        binding.tvNumberReturn.setOnClickListener {

        }
        binding.tvNumberOk.setOnClickListener {

            /*childFragmentManager.beginTransaction()
                .add(AddPayInfoFragment().apply {
                    arguments=Bundle().apply {
                        putString("input_money",binding.tvMoney.text.toString())
                    }
                },"xcx")
                .commitNowAllowingStateLoss()
            dismiss()*/
            startActivity(Intent(activity, AddPayInfoActivity::class.java).apply {
                putExtra("input_money", binding.tvMoney.text.toString())
            })
            dismiss()

        }


    }

    private fun updateMoneyText(number: String) {
        val oldMoney = binding.tvMoney.text.toString()
        //only one point
        if (oldMoney.contains(".") && number == ".") {
            return
        }
        //初始值为0,重复点击0
        if (oldMoney == "0" && number == "0") {
            return
        }
        //默认值0，点击其他数字，修改为点击的数字
        if (oldMoney == "0" && number != ".") {
            binding.tvMoney.text = number
            return
        }
        val new = oldMoney + number
//        binding.tvMoney.text = "$oldMoney$number"
        binding.tvMoney.text = new
    }

    private fun deleteMoneyText() {
        val oldMoney = binding.tvMoney.text.toString()
        //默认值0, 不删除
        if (oldMoney == "0") {
            return
        }
        if (oldMoney.length == 1 && oldMoney != "0") {
            binding.tvMoney.text = "0"
            return
        }
        val new = oldMoney.substring(0, oldMoney.length - 1)
        binding.tvMoney.text = new
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val params = attributes
            params.gravity = Gravity.BOTTOM
            attributes = params
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun initData() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}