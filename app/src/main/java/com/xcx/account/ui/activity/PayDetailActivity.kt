package com.xcx.account.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.xcx.account.R
import com.xcx.account.databinding.ActivityPayDetailBinding
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.ui.dialog.showCommonDialog
import com.xcx.account.ui.fragment.ChangePayNoteDialog
import com.xcx.account.utils.*
import com.xcx.account.viewmodel.PayInfoDetailModel
import java.util.*

class PayDetailActivity : BaseActivity() {

    private val TAG = "PayDetailActivity"
    private val DIALOG_PAY_NOTE_TAG = "PayNote"
    lateinit var binding: ActivityPayDetailBinding
    private var id = 0L
    private var bean: PayInfoBean? = null
    private val payInfoModel: PayInfoDetailModel by viewModels()

    override fun getContentView(): View {
        binding = ActivityPayDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun afterSetContentView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.tlToolbar)
        initData()
        initListener()
    }

    private fun initData() {
        id = intent.getLongExtra(PAY_ID_KEY, 0)
        logd(TAG, "initData id: $id")
        if (id != 0L) {
            payInfoModel.getPayInfoById(id)
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
            showChangePayNoteDialog()
        }

        binding.ivToolbarDelete.setOnClickListener {
            showCommonDialog(this,
                getString(R.string.dialog_delete_pay_title),
                getString(R.string.dialog_delete_pay_msg)) {

                onConfirm {
                    val id = bean?.id ?: -1L
                    if (id > -1L) {
                        payInfoModel.deletePayInfoById(PayInfoBean(id, "", "", 0L, "", 0L, "", ""))
                    }
                }
            }
        }

        payInfoModel.deletePayInfo.observe(this) {
            if (it > 0) {
                showToast(getString(R.string.delete_pay_success))
            }
            finish()
        }

        payInfoModel.payInfo.observe(this) {
            bean = it
            logd(TAG, "observe thread: ${Thread.currentThread().name}")
            bean?.apply {
                val money = "-¥${getMoneyWithTwoDecimal(payMoney)}"
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
                    binding.tvDetailNote.text = getString(R.string.hint_add_pay_note)
                }
            }
        }

        payInfoModel.updateInfo.observe(this) {
            if (it > 0) {
                showToast(getString(R.string.update_pay_success))
            }
        }
    }

    private fun showChangePayNoteDialog() {
        val dialog = ChangePayNoteDialog().apply {
            arguments = Bundle().apply {
                putString(PAY_NOTE_KEY, bean?.payNote ?: "")
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
                    payInfoModel.updatePayInfo(b)
                }
            }
            onCancel {

            }
        }
        dialog.show(supportFragmentManager, DIALOG_PAY_NOTE_TAG)
    }

    private fun showDatePickDialog() {
        val date = getYearMonthAndDay()
        val dialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                logd(TAG, "year: $year, month: $month, dayOfMonth: $dayOfMonth")
                bean?.apply {
                    val c = Calendar.getInstance()
                    c.timeInMillis = payTime
                    c.set(Calendar.YEAR, year)
                    c.set(Calendar.MONTH, month)
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
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
                    payInfoModel.updatePayInfo(b)
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
            this, { _, hourOfDay, minute ->
                logd(TAG, "hourOfDay: $hourOfDay, minute: $minute")
                bean?.apply {
                    val c = Calendar.getInstance()
                    c.timeInMillis = payTime
                    c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    c.set(Calendar.MINUTE, minute)

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
                    payInfoModel.updatePayInfo(b)
                }
            }, h, m, true
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
}
