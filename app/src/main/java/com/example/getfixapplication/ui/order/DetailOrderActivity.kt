package com.example.getfixapplication.ui.order

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.getfixapplication.R
import com.example.getfixapplication.data.remote.order.StatusOrderBody
import com.example.getfixapplication.databinding.ActivityDetailOrderBinding
import com.example.getfixapplication.utils.*

import com.example.getfixapplication.utils.ConstVal.ORDER_ID
import com.example.getfixapplication.utils.ConstVal.ORDER_STATUS
import com.example.getfixapplication.utils.ConstVal.USER_ID_SESSION
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOrderActivity : AppCompatActivity() {

    private val detailorderVM: DetailOrderViewModel by viewModels()
    private lateinit var binding: ActivityDetailOrderBinding
    private lateinit var sharedPreferences : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(USER_ID_SESSION, MODE_PRIVATE)
        val token = sharedPreferences.getString(USER_ID_SESSION, null)

        val loading = showLoading(this)

        val orderId = intent.getStringExtra(ORDER_ID)
        binding.btnNext.isVisible = intent.getStringExtra(ORDER_STATUS) != "Pesanan Selesai"

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

        if (orderId != null && token != null) {
            getData(token, orderId)
        }

        val req = StatusOrderBody(
            keterangan = "Pesanan Selesai"
        )

        binding.btnNext.setOnClickListener {
            if (orderId != null) {
                detailorderVM.updateStatusOrderService(req, orderId).observe(this) {
                    when (it.status) {
                        Status.LOADING -> {
                            loading.show()
                        }
                        Status.SUCCESS -> {
                            loading.dismiss()
                            finish()
                        }
                        Status.ERROR -> {
                            loading.dismiss()
                            showToast(this, it.message.toString())
                        }
                    }
                }
            }
        }


    }

    private fun getData(token: String, orderId: String) {
        val loading = showLoading(this)
        detailorderVM.getOrdersService(token, orderId).observe(this) { data ->
            when (data.status) {
                Status.LOADING -> {
                    loading.show()
                }
                Status.SUCCESS -> {
                    loading.dismiss()
                    val stat = intent.getStringExtra(ORDER_STATUS)
                    binding.apply {
                        data.data?.apply {
                            tvDetailOrderId.text = orderId
                            if (userTeknisi != null) {
                                detailorderVM.getTeknisiService(userTeknisi).observe(this@DetailOrderActivity) { teknisi ->
                                    when (teknisi.status) {
                                        Status.LOADING -> {
                                        }
                                        Status.SUCCESS -> {
                                            tvDescNama.text = teknisi.data?.nama
                                            tvDetailOrderIdteknisi.text = teknisi.data?.username
                                            tvDetailorderCountRating.text = teknisi.data?.rating.toString()
                                        }
                                        Status.ERROR -> {
                                            loading.dismiss()
                                            showToast(this@DetailOrderActivity, "Error Mengambil Data Teknisi")
                                        }
                                    }
                                }
                            }
                            tvDescProcess.text = stat
                            tvAlamatLokasi.text = alamat
                            tvTanggal.text = jadwal
                            tvJam.text = waktu
                            tvDetailOrderDeskripsiTugas.text = deskripsi
                        }
                    }
                }
                Status.ERROR -> {
                    loading.dismiss()
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