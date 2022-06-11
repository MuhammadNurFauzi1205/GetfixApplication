package com.example.getfixapplication.ui.order

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.getfixapplication.databinding.ActivityPayOrderBinding
import com.example.getfixapplication.utils.ConstVal.ORDER_ID
import com.example.getfixapplication.utils.ConstVal.ORDER_STATUS
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN
import com.example.getfixapplication.utils.ConstVal.USER_TANGGAL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val orderId = intent.getStringExtra(ORDER_ID)
        val layanan = intent.getStringExtra(USER_LAYANAN)
        val jadwal = intent.getStringExtra(USER_TANGGAL)
        val ket = intent.getStringExtra(ORDER_STATUS)

        binding.apply {
            tvDetailOrderId.text = orderId
            detailInfo.text = layanan
            tanggalInfo.text = jadwal
            tvFinishOrder.text = ket
            btnNext.setOnClickListener {
                val intent = Intent(this@PayOrderActivity, DetailOrderActivity::class.java)
                intent.putExtra(ORDER_ID, orderId)
                intent.putExtra(ORDER_STATUS, ket)
                startActivity(intent)
                finish()
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

    }
}