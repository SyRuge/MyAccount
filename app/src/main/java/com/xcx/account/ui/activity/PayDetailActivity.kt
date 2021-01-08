package com.xcx.account.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xcx.account.R
import com.xcx.account.databinding.ActivityPayDetailBinding
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.ui.fragment.ChangePayNoteDialog
import com.xcx.account.utils.*
import kotlinx.coroutines.*
import java.util.*

class PayDetailActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private val TAG = "PayDetailActivity"

    lateinit var binding: ActivityPayDetailBinding
    private var id = 0L
    private var bean: PayInfoBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initListener()
    }

    private fun initData() {
        val id = intent.getLongExtra("pay_id", 0)
        logd(TAG, "initData id: $id")
        if (id != 0L) {
            launch {
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
                        binding.tvDetailNote.setTextColor(
                            resources.getColor(
                                R.color.primaryTextColor,
                                null
                            )
                        )
                        binding.tvDetailNote.text = payNote
                    } else {
                        binding.tvDetailNote.setTextColor(
                            resources.getColor(
                                R.color.secondaryTextColor,
                                null
                            )
                        )
                        binding.tvDetailNote.text = "点击添加备注"
                    }
                }

            }
        } else {
            logd(TAG, "initData invalid id: $id")
        }
    }

    private fun initListener() {
        binding.llDetailDate.setOnClickListener {
            showDatePickDialog()
        }
        binding.llDetailTime.setOnClickListener {
            showTimePickDialog()
        }
        binding.llDetailNote.setOnClickListener {
            val dialog = ChangePayNoteDialog().apply {
                arguments = Bundle().apply {
                    putString("pay_note", bean?.payNote ?: "")
                }
            }
            dialog.setDialogClickListener {
                onConfirm { newNote ->
                    val oriNote = bean?.payNote ?: ""
                    if (newNote == oriNote) {
                        return@onConfirm
                    }
                    bean?.apply {
                        val b = PayInfoBean(
                            id,
                            payId,
                            paySellerName,
                            payMoney,
                            payCategory,
                            payTime,
                            payDate,
                            newNote
                        )
                        updatePayInfo(b)
                        if (newNote.isNotEmpty()) {
                            binding.tvDetailNote.setTextColor(
                                resources.getColor(
                                    R.color.primaryTextColor,
                                    null
                                )
                            )
                            binding.tvDetailNote.text = newNote
                        } else {
                            binding.tvDetailNote.setTextColor(
                                resources.getColor(
                                    R.color.secondaryTextColor,
                                    null
                                )
                            )
                            binding.tvDetailNote.text = "点击添加备注"
                        }
                    }
                }
                onCancel {

                }
            }
            dialog.show(supportFragmentManager, "PayNote")
        }
    }

    private fun showDatePickDialog() {
        val date = getYearMonthAndDay()
        val dialog = DatePickerDialog(
            this,
            { view, year, month, dayOfMonth ->
                logd(TAG, "year: $year, month: $month, dayOfMonth: $dayOfMonth")
                val c = Calendar.getInstance()
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, month)
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                bean?.apply {
                    val b = PayInfoBean(
                        id,
                        payId,
                        paySellerName,
                        payMoney,
                        payCategory,
                        c.timeInMillis,
                        payDate,
                        payNote
                    )
                    updatePayInfo(b)
                    binding.tvDetailDate.text = formatDate(c.timeInMillis)
                }
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
            this,
            { view, hourOfDay, minute ->
                logd(TAG, "hourOfDay: $hourOfDay, minute: $minute")
                val c = Calendar.getInstance()
                c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                c.set(Calendar.MINUTE, minute)
                bean?.apply {
                    val b = PayInfoBean(
                        id,
                        payId,
                        paySellerName,
                        payMoney,
                        payCategory,
                        c.timeInMillis,
                        payDate,
                        payNote
                    )
                    updatePayInfo(b)
                    binding.tvDetailTime.text = formatDate(c.timeInMillis)
                }
            }, h, m, false
        )
        dialog.show()
    }

    private fun updatePayInfo(newBean: PayInfoBean) {
        launch {
            async(Dispatchers.IO) {
                PayRepository.updatePayInfo(newBean)
            }
            bean = newBean
            showToast("Update success!")
        }
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

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
