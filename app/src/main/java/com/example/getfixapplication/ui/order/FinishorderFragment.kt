package com.example.getfixapplication.ui.order

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getfixapplication.R
import com.example.getfixapplication.data.model.OrderListItem
import com.example.getfixapplication.databinding.FragmentFinishorderBinding
import com.example.getfixapplication.utils.*
import com.example.getfixapplication.utils.ConstVal.ORDER_ID
import com.example.getfixapplication.utils.ConstVal.ORDER_STATUS
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN

class FinishorderFragment : Fragment() {

    private lateinit var binding: FragmentFinishorderBinding
    private val finishOrderAdapter = FinishOrderAdapter()
    private val orderListItemVM: ListOrderViewModel by activityViewModels()

    private lateinit var sharedPreferences : SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishorderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData() {
        val loading = showLoading(context!!)
        binding.rvFinishOrder.adapter = finishOrderAdapter
        binding.rvFinishOrder.layoutManager = LinearLayoutManager(context)
        sharedPreferences = activity?.getSharedPreferences(
            ConstVal.USER_ID_SESSION,
            Context.MODE_PRIVATE
        )!!
        val userId = sharedPreferences.getString(ConstVal.USER_ID_SESSION, null)
//        loading.window?.attributes?.height = ViewGroup.LayoutParams.MATCH_PARENT
//        loading.window?.attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT

        if (userId != null) {
            orderListItemVM.getOrdersService(userId, 1).observe(viewLifecycleOwner) { data ->
                when (data.status) {
                    Status.LOADING -> {
                        loading.show()
                    }
                    Status.SUCCESS -> {
                        loading.dismiss()
                        setData(data.data!!)
                    }
                    Status.ERROR -> {
                        loading.dismiss()
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
        finishOrderAdapter.items = data
        finishOrderAdapter.setOnItemClickCallback(object : FinishOrderAdapter.OnItemClickCallback {
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