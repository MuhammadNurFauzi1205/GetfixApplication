package com.example.getfixapplication.ui.order

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.getfixapplication.R
import com.example.getfixapplication.data.model.OrderItem
import com.example.getfixapplication.data.model.TeknisiModel
import com.example.getfixapplication.databinding.ActivityDetailOrderBinding
import com.example.getfixapplication.utils.ConstVal.TEKNISI_FOTO
import com.example.getfixapplication.utils.ConstVal.TEKNISI_NAMA
import com.example.getfixapplication.utils.ConstVal.TEKNISI_RATING
import com.example.getfixapplication.utils.ConstVal.USER_DESC
import com.example.getfixapplication.utils.ConstVal.USER_JADWAL
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN
import com.example.getfixapplication.utils.ConstVal.USER_TANGGAL
import com.example.getfixapplication.utils.ConstVal.USER_TIPE_LAYANAN
import com.example.getfixapplication.utils.ConstVal.USER_WILAYAH
import com.example.getfixapplication.utils.Status
import com.example.getfixapplication.utils.showPositiveAlert
import com.example.getfixapplication.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOrderActivity : AppCompatActivity() {

    private val detailorderVM: DetailOrderViewModel by viewModels()
    private lateinit var binding: ActivityDetailOrderBinding

    var namaTeknisi: String =
        "Teknisi 1"

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val waktu = intent.getStringExtra(USER_JADWAL)
        val wilayah = intent.getStringExtra(USER_WILAYAH)
        val tanggal = intent.getStringExtra(USER_TANGGAL)
        val descLayanan = intent.getStringExtra(USER_DESC)
        val jenisLayanan = intent.getStringExtra(USER_TIPE_LAYANAN)
        val teknisiNama = intent.getStringExtra(TEKNISI_NAMA)
        val teknisiRating = intent.getStringExtra(TEKNISI_RATING)
        val teknisiFoto = intent.getStringExtra(TEKNISI_FOTO)
        val layanan = intent.getStringExtra(USER_LAYANAN)



        when (layanan) {
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

        Log.e("data", "$waktu $wilayah $tanggal $descLayanan $jenisLayanan $layanan " +
                "$teknisiNama $teknisiFoto $teknisiRating")


        binding.btnNext.setOnClickListener {

            val request = OrderItem(
                tanggal.toString(),
                jenisLayanan.toString(),
                namaTeknisi,
                waktu.toString(),
                wilayah.toString(),
                descLayanan.toString(),
                "22 mei 2022",
                "19.00"
            )
            bookTeknisi(request)

        }


    }

    private fun bookTeknisi(addBook: OrderItem) {
        detailorderVM.addOrdersService(addBook).observe(this) { data ->
            when (data.status) {
                Status.LOADING -> {
                    showToast(this, "LOADING")
                }
                Status.SUCCESS -> {
                    showToast(this, data.data?.message.toString())
                    intent.getStringExtra(USER_JADWAL)
                    intent.getStringExtra(USER_WILAYAH)
                    intent.getStringExtra(USER_TANGGAL)
                    binding.infoInvoice.text = addBook.infoInvoice
                    binding.tvDescNama.text = addBook.nameTechnision
                    binding.textView2.text = addBook.id
                    binding.tvAlamatLokasi.text = addBook.alamat
                    binding.tvTanggal.text=addBook.tanggal
                    binding.tvJam.text=addBook.waktu
                    Glide.with(this@DetailOrderActivity)
                        .load(data)
                        .into(binding.ivFoto)
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