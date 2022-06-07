package com.example.getfixapplication.ui.booking

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getfixapplication.R
import com.example.getfixapplication.data.model.TeknisiModel
import com.example.getfixapplication.data.remote.order.OrdersBody
import com.example.getfixapplication.databinding.ActivityPilihTeknisiBinding
import com.example.getfixapplication.ui.order.DetailOrderActivity
import com.example.getfixapplication.utils.ConstVal.TEKNISI_FOTO
import com.example.getfixapplication.utils.ConstVal.TEKNISI_NAMA
import com.example.getfixapplication.utils.ConstVal.TEKNISI_RATING
import com.example.getfixapplication.utils.ConstVal.USERNAME
import com.example.getfixapplication.utils.ConstVal.USER_ALAMAT
import com.example.getfixapplication.utils.ConstVal.USER_DESC
import com.example.getfixapplication.utils.ConstVal.USER_ID_SESSION
import com.example.getfixapplication.utils.ConstVal.USER_JADWAL
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN
import com.example.getfixapplication.utils.ConstVal.USER_TANGGAL
import com.example.getfixapplication.utils.ConstVal.USER_JENIS_TUGAS
import com.example.getfixapplication.utils.ConstVal.USER_WILAYAH
import com.example.getfixapplication.utils.Status
import com.example.getfixapplication.utils.showPositiveAlert
import com.example.getfixapplication.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PilihTeknisiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPilihTeknisiBinding
    var namaTeknisi: String =
        "Teknisi 1"
    private lateinit var idTeknisi: String

    val data = listOf(
        TeknisiModel(
            "Teknisi 1",
            "Jl. Teknik 1",
            "1",
            "https://i.pinimg.com/736x/47/a8/24/47a824c67db1ec78cc9f9011ba52022e--wallpaper-lucu-foto-lucu.jpg",
            4.5f
        ),
        TeknisiModel(
            "Teknisi 2",
            "Jl. Teknik 2",
            "2",
            "https://i.ytimg.com/vi/kinwDmOO5ns/maxresdefault.jpg",
            4.3f
        ),
        TeknisiModel(
            "Teknisi 3",
            "Jl. Teknik 3",
            "3",
            "https://4.bp.blogspot.com/_jJu6heRzjEA/TGJc5QBw86I/AAAAAAAAAZA/fzxpbgl-vYs/w1200-h630-p-k-no-nu/binatang+lucu.jpg",
            3.8f
        )
    )

    private val bookVM: BookingViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilihTeknisiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(USER_ID_SESSION, MODE_PRIVATE)
        val userId = sharedPreferences.getString(USERNAME, null)

        val waktu = intent.getStringExtra(USER_JADWAL)
        val wilayah = intent.getStringExtra(USER_WILAYAH)
        val tanggal = intent.getStringExtra(USER_TANGGAL)
        val descLayanan = intent.getStringExtra(USER_DESC)
        val jenisLayanan = intent.getStringExtra(USER_JENIS_TUGAS)
        val layanan = intent.getStringExtra(USER_LAYANAN)
        val alamat = intent.getStringExtra(USER_ALAMAT)

        val teknisiAdapter = TeknisiAdapter()

        binding.rvTeknisi.layoutManager = LinearLayoutManager(this)
        binding.rvTeknisi.adapter = teknisiAdapter

        teknisiAdapter.items = data
        teknisiAdapter.setOnItemClickCallback(object : TeknisiAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TeknisiModel) {
                Log.e("data", "$waktu $wilayah $tanggal $descLayanan $jenisLayanan $layanan")
                namaTeknisi = data.nama
                idTeknisi = data.id

            }
        })

        binding.topAppBar.setNavigationOnClickListener { finish() }

        binding.btnPesanTeknisi.setOnClickListener {
            val request = OrdersBody(
                tanggal,
                jenisLayanan,
                namaTeknisi,
                idTeknisi,
                descLayanan,
                null,
                userId,
                wilayah,
                alamat
            )
            bookTeknisi(request)
        }

    }

    private fun bookTeknisi(addBook: OrdersBody) {
        bookVM.addOrdersService(addBook).observe(this) { data ->
            when (data.status) {
                Status.LOADING -> {
                    showToast(this, "LOADING")
                }
                Status.SUCCESS -> {
                    showToast(this, data.data?.message.toString())
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