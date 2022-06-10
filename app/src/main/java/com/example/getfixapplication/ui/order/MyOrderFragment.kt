package com.example.getfixapplication.ui.order

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getfixapplication.R
import com.example.getfixapplication.data.model.OrderListItem
import com.example.getfixapplication.databinding.FragmentMyorderBinding
import com.example.getfixapplication.utils.ConstVal
import com.example.getfixapplication.utils.ConstVal.ORDER_ID
import com.example.getfixapplication.utils.ConstVal.ORDER_STATUS
import com.example.getfixapplication.utils.ConstVal.USER_ID_SESSION
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN
import com.example.getfixapplication.utils.Status
import com.example.getfixapplication.utils.showPositiveAlert
import com.example.getfixapplication.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrderFragment : Fragment() {

    private lateinit var binding: FragmentMyorderBinding
    private val orderListItemVM: ListOrderViewModel by viewModels()
    private val myOrderAdapter = MyOrderAdapter()
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyorderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData() {
        binding.rvMyOrder.adapter = myOrderAdapter
        binding.rvMyOrder.layoutManager = LinearLayoutManager(context)
        sharedPreferences = activity?.getSharedPreferences(
            USER_ID_SESSION,
            MODE_PRIVATE
        )!!
        val userId = sharedPreferences.getString(USER_ID_SESSION, null)

        if (userId != null) {
            orderListItemVM.getOrdersService(userId, 0).observe(viewLifecycleOwner) { data ->
                when (data.status) {
                    Status.LOADING -> {
                        showToast(requireContext(), "LOADING")
                    }
                    Status.SUCCESS -> {
                        showToast(requireContext(), data.message.toString())
                        setData(data.data!!)
                    }
                    Status.ERROR -> {
                        showPositiveAlert(
                            requireContext(),
                            getString(R.string.error_data),
                            data.message.toString()
                        )
                    }
                }
            }
        }
    }

    private fun setData(data: List<OrderListItem>) {
        myOrderAdapter.items = data
        myOrderAdapter.setOnItemClickCallback(object : MyOrderAdapter.OnItemClickCallback {
            override fun onItemClicked(data: OrderListItem) {
                val intent = Intent(activity, DetailOrderActivity::class.java)
                intent.putExtra(ORDER_ID, data.orderId)
                intent.putExtra(USER_LAYANAN, data.jenisOrder)
                intent.putExtra(ORDER_STATUS, data.statusOrder)
                startActivity(intent)
            }
        })
    }
}