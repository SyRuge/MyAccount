package com.xcx.account.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcx.account.adapter.TodayPayAdapter
import com.xcx.account.adapter.TotalPayAdapter
import com.xcx.account.bean.HomePayBean
import com.xcx.account.bean.HomeTotalPayBean
import com.xcx.account.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        initData()
    }

    private fun initData() {
        binding.rvPayContent.layoutManager = LinearLayoutManager(activity)
        var list = mutableListOf<HomePayBean>()
        list.add(HomePayBean("0", "华莱士", -22.45, "外卖", "2020-12-04 18:04"))
        list.add(HomePayBean("1", "三米", -32.56, "充值缴费", "2020-12-15 20:54"))
        list.add(HomePayBean("2", "华莱士", -52.71, "外卖", "2020-12-17 16:34"))
        list.add(HomePayBean("3", "三米", -104.45, "充值缴费", "2020-12-18 06:17"))
        var adapter = TodayPayAdapter(payList = list)
        binding.rvPayContent.adapter = adapter

        binding.rvTotalPayContent.layoutManager = LinearLayoutManager(activity)
        var totalList = mutableListOf<HomeTotalPayBean>()
        totalList.add(HomeTotalPayBean("0", "本日", -23.56, "", "2020-12-21"))
        totalList.add(HomeTotalPayBean("1", "本周", -73.49, "", "12-21 ~ 12-27"))
        totalList.add(HomeTotalPayBean("2", "本月", -193.52, "", "12-01 ~ 12-31"))
        totalList.add(HomeTotalPayBean("3", "本年", -1023.77, "", "01-01 ~ 12-31"))
        var totalAdapter = TotalPayAdapter(payList = totalList)
        binding.rvTotalPayContent.adapter = totalAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}