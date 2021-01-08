package com.xcx.account.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcx.account.R
import com.xcx.account.adapter.TodayPayAdapter
import com.xcx.account.adapter.TotalPayAdapter
import com.xcx.account.bean.HomePayBean
import com.xcx.account.bean.HomeTotalPayBean
import com.xcx.account.databinding.FragmentCountBinding
import com.xcx.account.repository.database.PayRepository
import com.xcx.account.repository.database.table.PayInfoBean
import java.math.BigDecimal

class CountFragment : Fragment() {

    var id = 0L
    private var _binding: FragmentCountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
    }

    private fun initData() {

    }

    private fun initListener() {
        binding.btnInsert.setOnClickListener {
//            PayRepository.addPayInfo(PayInfoBean(0, "1", "三米", 2356, "外卖", 0, "2020-12-04 18:04",""))
        }
        binding.btnDelete.setOnClickListener {
            val bean = PayInfoBean(id, "1", "三米", 2356, "外卖", 0, "2020-12-04 18:04","")
            PayRepository.deletePayInfo(bean)
        }
        binding.btnUpdate.setOnClickListener {
            val bean = PayInfoBean(id, "1", "update", 2356, "外卖", 0, "2020-12-04 18:04","")
//            PayRepository.updatePayInfo(bean)
        }
        binding.btnQuery.setOnClickListener {
            PayRepository.getAllPayInfo()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}