package com.example.getfixapplication.ui.order

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.getfixapplication.R
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
                binding.jenisLayanan.text = getString(R.string.teknisi_laptop)
                binding.ivBookingLayanan.setImageResource(R.drawable.laptop)
            }
            "Komputer" -> {
                binding.jenisLayanan.text = getString(R.string.teknisi_komputer)
                binding.ivBookingLayanan.setImageResource(R.drawable.workstation)
            }
            "Televisi" -> {
                binding.jenisLayanan.text = getString(R.string.teknisi_televisi)
                binding.ivBookingLayanan.setImageResource(R.drawable.tv)
            }
            "Handphone" -> {
                binding.jenisLayanan.text = getString(R.string.teknisi_handphone)
                binding.ivBookingLayanan.setImageResource(R.drawable.touchscreen)
            }
        }

        if (orderId != null) {
            getData(orderId)
        }


    }

    private fun getData(orderId: String) {
        detailorderVM.getOrdersService(orderId).observe(this) { data ->
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
                            if (idTeknisi != null) {
                                detailorderVM.getTeknisiService(idTeknisi).observe(this@DetailOrderActivity) { teknisi ->
                                    when (teknisi.status) {
                                        Status.LOADING -> {
                                            showToast(this@DetailOrderActivity, "Mengambil Data Teknisi")
                                        }
                                        Status.SUCCESS -> {
                                            tvDescNama.text = teknisi.data?.nama
                                            tvDetailOrderIdteknisi.text = teknisi.data?.username
                                            tvDetailorderCountRating.text = teknisi.data?.rating.toString()
                                        }
                                        Status.ERROR -> {
                                            showToast(this@DetailOrderActivity, "Error Mengambil Data Teknisi")
                                        }
                                    }
                                }
                            }
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