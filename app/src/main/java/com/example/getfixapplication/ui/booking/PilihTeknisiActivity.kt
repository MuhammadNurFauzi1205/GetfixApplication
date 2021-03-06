package com.example.getfixapplication.ui.booking

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getfixapplication.R
import com.example.getfixapplication.data.model.Teknisi
import com.example.getfixapplication.data.remote.order.OrdersBody
import com.example.getfixapplication.databinding.ActivityPilihTeknisiBinding
import com.example.getfixapplication.ui.order.PayOrderActivity
import com.example.getfixapplication.utils.ConstVal.ORDER_ID
import com.example.getfixapplication.utils.ConstVal.ORDER_STATUS
import com.example.getfixapplication.utils.ConstVal.USERNAME
import com.example.getfixapplication.utils.ConstVal.USER_ALAMAT
import com.example.getfixapplication.utils.ConstVal.USER_DESC
import com.example.getfixapplication.utils.ConstVal.USER_ID_SESSION
import com.example.getfixapplication.utils.ConstVal.USER_JADWAL
import com.example.getfixapplication.utils.ConstVal.USER_JENIS_TUGAS
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN
import com.example.getfixapplication.utils.ConstVal.USER_TANGGAL
import com.example.getfixapplication.utils.ConstVal.USER_WILAYAH
import com.example.getfixapplication.utils.Status
import com.example.getfixapplication.utils.showLoading
import com.example.getfixapplication.utils.showPositiveAlert
import com.example.getfixapplication.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PilihTeknisiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPilihTeknisiBinding
    var namaTeknisi: String = ""
    private val teknisiAdapter = TeknisiAdapter()

    private val bookVM: BookingViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilihTeknisiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var idTeknisi: String? = null
        sharedPreferences = getSharedPreferences(USER_ID_SESSION, MODE_PRIVATE)
        val userId = sharedPreferences.getString(USERNAME, null)

        val waktu = intent.getStringExtra(USER_JADWAL)
        val wilayah = intent.getStringExtra(USER_WILAYAH)
        val jadwal = intent.getStringExtra(USER_TANGGAL)
        val descLayanan = intent.getStringExtra(USER_DESC)
        val jenisLayanan = intent.getStringExtra(USER_JENIS_TUGAS)
        val layanan = intent.getStringExtra(USER_LAYANAN)
        val alamat = intent.getStringExtra(USER_ALAMAT)

        val loading = showLoading(this)

        binding.rvTeknisi.layoutManager = LinearLayoutManager(this)
        binding.rvTeknisi.adapter = teknisiAdapter

        if (layanan != null && wilayah != null) {
            bookVM.getTeknisiService(layanan, wilayah).observe(this) { data ->
                when (data.status) {
                    Status.LOADING -> {
                        loading.show()
                    }
                    Status.SUCCESS -> {
                        loading.dismiss()
                        teknisiAdapter.items = data.data!!
                        teknisiAdapter.setOnItemClickCallback(object :
                            TeknisiAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: Teknisi) {
                                Log.e("data", "$waktu $wilayah $descLayanan $jenisLayanan $layanan")
                                namaTeknisi = data.nama
                                idTeknisi = data.username
                            }
                        })
                    }
                    Status.ERROR -> {
                        loading.dismiss()
                        showPositiveAlert(this, "Ups!! Error", data.message!!)
                    }
                }
            }
        }

        binding.topAppBar.setNavigationOnClickListener { finish() }

        binding.btnPesanTeknisi.setOnClickListener {
            if (idTeknisi == null) {
                showToast(this, "Pilih Teknisi Terlebih Dahulu")
            } else {
                val request = OrdersBody(
                    jadwal,
                    layanan,
                    idTeknisi,
                    waktu,
                    wilayah,
                    descLayanan,
                    userId,
                    alamat
                )
                bookTeknisi(request, jadwal, layanan)
            }
        }

    }

    private fun bookTeknisi(addBook: OrdersBody, jadwal: String?, layanan: String?) {
        val loading = showLoading(this)
        bookVM.addOrdersService(addBook).observe(this) { data ->
            when (data.status) {
                Status.LOADING -> {
                    loading.show()
                }
                Status.SUCCESS -> {
                    loading.dismiss()
                    val id = data.data?.data?.pesanan?.id.toString()
                    val ket = data.data?.data?.pesanan?.keterangan.toString()
                    // move to pay activity
                    val pay = Intent(this, PayOrderActivity::class.java)
                    pay.putExtra(ORDER_ID, id)
                    pay.putExtra(ORDER_STATUS, ket)
                    pay.putExtra(USER_TANGGAL, jadwal)
                    pay.putExtra(USER_LAYANAN, layanan)
                    startActivity(pay)
                    finish()
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