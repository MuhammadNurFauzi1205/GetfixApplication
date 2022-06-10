package com.example.getfixapplication.ui.order

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.getfixapplication.databinding.ActivityPayOrderBinding
import com.example.getfixapplication.utils.ConstVal.ORDER_ID
import com.example.getfixapplication.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayOrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPayOrderBinding
    private lateinit var sharedPreferences : SharedPreferences
    private val payOrderViewModel : PayOrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(ORDER_ID, MODE_PRIVATE)!!
        val orderId = sharedPreferences.getString(ORDER_ID, null)

        if (orderId != null) {
            Log.e("orderId", orderId)
        }

        if (orderId != null) {
            getData(orderId)
            binding.tvDetailOrderId.text = orderId
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

    }

    private fun getData(orderId: String) {
        payOrderViewModel.getOrdersService(orderId, 0).observe(this) { data ->
            when (data.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    binding.apply {
                        data.data?.map {
                            tanggalInfo.text = it.tanggalOrder
                            detailInfo.text = it.jenisOrder
                            tvFinishOrder.text = it.statusOrder
                        }
                        btnNext.setOnClickListener {
                            val intent = Intent(this@PayOrderActivity, DetailOrderActivity::class.java)
                            intent.putExtra(ORDER_ID, orderId)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                Status.ERROR -> {

                }
            }
        }
    }

}