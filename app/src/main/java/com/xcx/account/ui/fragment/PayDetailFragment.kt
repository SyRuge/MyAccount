package com.xcx.account.ui.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import com.xcx.account.databinding.FragmentPayDetailBinding
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.utils.*
import kotlinx.coroutines.*
import java.util.*


class PayDetailFragment : Fragment() {

    private val TAG = "PayDetailFragment"
    private var _binding: FragmentPayDetailBinding? = null
    private val binding get() = _binding!!
    private val scope = MainScope()
    private var id = 0L
    private var bean: PayInfoBean? = null

    lateinit var context: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            id = getLong("pay_id", 0)
            logd(TAG, "onCreate id: $id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPayDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
    }

    private fun initData() {
        logd(TAG, "initData id: $id")
        if (id != 0L) {
            scope.launch {
                val deferred = async(Dispatchers.IO) {
                    logd(TAG, "async thread: ${Thread.currentThread().name}")
                    PayRepository.getPayInfoById(id)
                }
                bean = deferred.await()
                logd(TAG, "await thread: ${Thread.currentThread().name}")
                bean?.apply {
                    val money = "-￥${getMoneyWithTwoDecimal(payMoney)}"
                    binding.tvDetailMoney.text = money
                    binding.tvDetailCategory.text = payCategory
                    binding.tvDetailDate.text = formatDate(payTime)
                    binding.tvDetailTime.text = getTime(payTime)
                    if (payNote.isNotEmpty()) {
                        binding.etDetailNote.setText(payNote)
                    }
                }

            }
        } else {
        }
    }

    private fun initListener() {
        binding.llDetailDate.setOnClickListener {
            showDatePickDialog()
        }
        binding.llDetailTime.setOnClickListener {
            showTimePickDialog()
        }
    }

    private fun showDatePickDialog() {
        val date = getYearMonthAndDay()
        val dialog = DatePickerDialog(
            context,
            { view, year, month, dayOfMonth ->
                logd(TAG, "year: $year, month: $month, dayOfMonth: $dayOfMonth")
            }, date[0], date[1], date[2]
        )
        dialog.show()
    }

    private fun showTimePickDialog() {

        var h = 0
        var m = 0
        bean?.apply {
            h = getHourOfDay(payTime)
            m = getHourOfMinute(payTime)
        }
        val dialog = TimePickerDialog(
            context,
            { view, hourOfDay, minute ->
                logd(TAG, "hourOfDay: $hourOfDay, minute: $minute")
            }, h, m, false
        )
        dialog.show()
    }

    private fun getYearMonthAndDay(): Array<Int> {
        val c = Calendar.getInstance()
        val date =
            arrayOf(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
        bean?.apply {
            c.timeInMillis = payTime
            date[0] = c.get(Calendar.YEAR)
            date[1] = c.get(Calendar.MONTH)
            date[2] = c.get(Calendar.DAY_OF_MONTH)
        }
        return date
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        scope.cancel()
    }
}
