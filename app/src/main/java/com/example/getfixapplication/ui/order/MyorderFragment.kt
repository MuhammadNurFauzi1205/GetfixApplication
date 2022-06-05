package com.example.getfixapplication.ui.order

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getfixapplication.databinding.FragmentMyorderBinding
import com.example.getfixapplication.utils.ConstVal.USER_ID_SESSION

class MyorderFragment : Fragment() {

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
        binding.rvMyOrder.adapter = myOrderAdapter
        binding.rvMyOrder.layoutManager = LinearLayoutManager(context)
        getData()
    }

    private fun getData() {
        sharedPreferences = activity?.getSharedPreferences(
            USER_ID_SESSION,
            MODE_PRIVATE
        )!!
        val userId = sharedPreferences.getString(USER_ID_SESSION, null)

        if (userId != null) {
            orderListItemVM.getOrdersService(userId).observe(viewLifecycleOwner) {
                myOrderAdapter.items = it.data!!
            }
        }
    }
}