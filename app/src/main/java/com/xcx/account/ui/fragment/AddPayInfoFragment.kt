package com.xcx.account.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.xcx.account.adapter.PayCategoryAdapter
import com.xcx.account.bean.PayCategoryBean

import com.xcx.account.databinding.FragmentAddPayInfoBinding
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.repository.database.table.PayInfoBean
import com.xcx.account.utils.logd
import com.xcx.account.utils.showToast
import java.math.BigDecimal


class AddPayInfoFragment : Fragment() {

    private val TAG = "AddPayInfoFragment"
    private var _binding: FragmentAddPayInfoBinding? = null
    private val binding get() = _binding!!
    private var oriMoney = ""
    private val KEY_INPUT_MONEY = "input_money"
    private var selectCategory = "餐饮"
    lateinit var activity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            oriMoney = it.getString(KEY_INPUT_MONEY, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddPayInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
    }

    private fun initData() {
        formatPayMoney()
        val list = getPayCategoryList()
        binding.rvPayCategory.layoutManager = GridLayoutManager(activity, 3)
        val payCategoryAdapter = PayCategoryAdapter(categoryList = list)
        binding.rvPayCategory.adapter = payCategoryAdapter
        payCategoryAdapter.setOnItemClickListener(object : PayCategoryAdapter.OnItemClickListener {
            override fun onItemClick(bean: PayCategoryBean) {
                selectCategory = bean.payCategory
            }
        })
    }

    private fun formatPayMoney() {
        logd(TAG,"formatPayMoney oriMoney: $oriMoney")
        if (oriMoney.isNotEmpty()) {
            val ori = BigDecimal(oriMoney)
            val b1 = ori.multiply(BigDecimal.valueOf(100L))

            val b2 = b1.divide(BigDecimal.valueOf(100.0)).setScale(2, BigDecimal.ROUND_DOWN)
            val money = "-￥$b2"
            binding.tvPayMoney.text = money
        }
    }

    private fun initListener() {

        binding.btnAddPayInfo.setOnClickListener {
            if (oriMoney.isEmpty()) {
                return@setOnClickListener
            }
            val ori = BigDecimal(oriMoney)
            val money = ori.multiply(BigDecimal.valueOf(100)).toLong()
            PayRepository.addPayInfo(
                PayInfoBean(
                    0,
                    "0",
                    selectCategory,
                    money,
                    selectCategory,
                    System.currentTimeMillis(),
                    "",
                    ""
                )
            )
            activity.finish()
        }
    }

    private fun getPayCategoryList(): MutableList<PayCategoryBean> {
        //餐饮 购物 日用 交通 买菜 水果 零食 运动 全部
        var list = mutableListOf<PayCategoryBean>()
        list.add(PayCategoryBean("餐饮", true))
        list.add(PayCategoryBean("水果"))
        list.add(PayCategoryBean("购物"))
        list.add(PayCategoryBean("日用"))
        list.add(PayCategoryBean("交通"))
        list.add(PayCategoryBean("买菜"))
        list.add(PayCategoryBean("零食"))
        list.add(PayCategoryBean("运动"))
        list.add(PayCategoryBean("全部"))
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
