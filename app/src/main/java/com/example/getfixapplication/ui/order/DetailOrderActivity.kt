package com.example.getfixapplication.ui.order

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.getfixapplication.R
import com.example.getfixapplication.data.model.TeknisiModel
import com.example.getfixapplication.databinding.ActivityDetailOrderBinding
import com.example.getfixapplication.utils.ConstVal.ORDER_ID
import com.example.getfixapplication.utils.ConstVal.USER_ID_SESSION
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN
import com.example.getfixapplication.utils.Status
import com.example.getfixapplication.utils.showPositiveAlert
import com.example.getfixapplication.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOrderActivity : AppCompatActivity() {

    private val detailorderVM: DetailOrderViewModel by viewModels()
    private lateinit var binding: ActivityDetailOrderBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getStringExtra(ORDER_ID)

        binding.topDetailOrder.setNavigationOnClickListener {
            finish()
        }

        when (intent.getStringExtra(USER_LAYANAN)) {
            "Laptop" -> {
                binding.jenislayanan.text = getString(R.string.teknisi_laptop)
                binding.ivBookingLayanan.setImageResource(R.drawable.laptop)
            }
            "Komputer" -> {
                binding.jenislayanan.text = getString(R.string.teknisi_komputer)
                binding.ivBookingLayanan.setImageResource(R.drawable.workstation)
            }
            "Televisi" -> {
                binding.jenislayanan.text = getString(R.string.teknisi_televisi)
                binding.ivBookingLayanan.setImageResource(R.drawable.tv)
            }
            "Handphone" -> {
                binding.jenislayanan.text = getString(R.string.teknisi_handphone)
                binding.ivBookingLayanan.setImageResource(R.drawable.touchscreen)
            }
        }

        if (orderId != null) {
            getData(orderId)
        }


    }

    private fun getData(orderId: String) {
        detailorderVM.addOrdersService(orderId).observe(this) { data ->
            when (data.status) {
                Status.LOADING -> {
                    showToast(this, "LOADING")
                }
                Status.SUCCESS -> {
                    showToast(this, "SUCCESS")

                    intent.getStringExtra(USER_ID_SESSION)

                    binding.apply {
                        data.data?.apply {
                            tvDetailOrderId.text = orderId
                            tvDescNama.text = "Teknisi 1"
                            tvDetailOrderIdteknisi.text = "T123"
                            tvAlamatLokasi.text = alamat
                            tvTanggal.text = jadwal
                            tvDetailOrderDeskripsiTugas.text = deskripsi
                        }
                    }
                }
                Status.ERROR -> {
                    showPositiveAlert(
                        this,
                        getString(R.string.error_data),
                        data.message.toString()
                    )
                }
            }
        }
    }



}