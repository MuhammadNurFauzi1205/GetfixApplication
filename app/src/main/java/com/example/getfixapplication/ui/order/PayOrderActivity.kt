package com.example.getfixapplication.ui.order

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.getfixapplication.R
import com.example.getfixapplication.databinding.ActivityPayOrderBinding
import com.example.getfixapplication.utils.ConstVal
import com.example.getfixapplication.utils.ConstVal.ORDER_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayOrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPayOrderBinding
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(ORDER_ID, MODE_PRIVATE)!!
        val orderId = sharedPreferences.getString(ORDER_ID, null)

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

    }

}