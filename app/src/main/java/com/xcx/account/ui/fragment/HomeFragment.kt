package com.xcx.account.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcx.account.adapter.TodayPayAdapter
import com.xcx.account.adapter.TotalPayAdapter
import com.xcx.account.bean.HomePayBean
import com.xcx.account.bean.HomeTotalPayBean
import com.xcx.account.databinding.FragmentHomeBinding
import com.xcx.account.utils.logd
import com.xcx.account.viewmodel.PayViewModel
import java.util.*

class HomeFragment : Fragment() {

    private var TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var payModel: PayViewModel
    var payAdapter: TodayPayAdapter? = null
    var totalAdapter: TotalPayAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        payModel = ViewModelProvider(this).get(PayViewModel::class.java)
        initData()
        initListener()
    }

    private fun initData() {
        /* var list = mutableListOf<HomePayBean>()
         list.add(HomePayBean("0", "华莱士", -22.45, "外卖", "2020-12-04 18:04"))
         list.add(HomePayBean("1", "三米", -32.56, "充值缴费", "2020-12-15 20:54"))
         list.add(HomePayBean("2", "华莱士", -52.71, "外卖", "2020-12-17 16:34"))
         list.add(HomePayBean("3", "华莱士", -52.71, "外卖", "2020-12-17 16:34"))
         list.add(HomePayBean("4", "三米", -104.45, "充值缴费", "2020-12-18 06:17"))
         list.add(HomePayBean("5", "三米", -104.45, "充值缴费", "2020-12-18 06:17"))*/

        binding.rvTotalPayContent.layoutManager = LinearLayoutManager(activity)
        var totalList = mutableListOf<HomeTotalPayBean>()
        totalList.add(HomeTotalPayBean("0", "本日", -23.56, "", "2020-12-21"))
        totalList.add(HomeTotalPayBean("1", "本周", -73.49, "", "12-21 ~ 12-27"))
        totalList.add(HomeTotalPayBean("2", "本月", -193.52, "", "12-01 ~ 12-31"))
        totalList.add(HomeTotalPayBean("3", "本年", -1023.77, "", "01-01 ~ 12-31"))
        totalList.add(HomeTotalPayBean("4", "本年", -1023.77, "", "01-01 ~ 12-31"))
        totalList.add(HomeTotalPayBean("5", "本年", -1023.77, "", "01-01 ~ 12-31"))
        var totalAdapter = TotalPayAdapter(payList = totalList)
        binding.rvTotalPayContent.adapter = totalAdapter
    }

    private fun initListener() {
        binding.srlRefreshPayInfo.run {
            postDelayed({ isRefreshing = false }, 1500)
        }
        payModel.allPayInfo.observe(viewLifecycleOwner) { list ->

            //update today pay info
            if (payAdapter == null) {
                binding.rvPayContent.layoutManager = LinearLayoutManager(activity)
                payAdapter = TodayPayAdapter(payList = list)
                binding.rvPayContent.adapter = payAdapter
            } else {
                payAdapter?.updatePayInfoData(list)
            }

            updateTotalPayUi(list)
        }
    }

    /**
     * sum total pay
     */
    private fun updateTotalPayUi(list: List<HomePayBean>) {
        //1. 计算本日支出
        //2. 计算本周支出
        //3. 计算本月支出
        //4. 计算本年支出
        Calendar.getInstance()
        var can:Calendar
        /*if (totalAdapter == null) {
            binding.rvTotalPayContent.layoutManager = LinearLayoutManager(activity)
            totalAdapter = TotalPayAdapter(payList = list)
            binding.rvTotalPayContent.adapter = payAdapter
        } else {
            totalAdapter?.updatePayInfoData(list)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}