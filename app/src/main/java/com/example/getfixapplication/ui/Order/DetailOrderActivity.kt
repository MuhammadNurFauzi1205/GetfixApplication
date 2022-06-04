package com.example.getfixapplication.ui.Order

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.getfixapplication.R
import com.example.getfixapplication.data.model.OrderItem
import com.example.getfixapplication.data.model.TeknisiModel
import com.example.getfixapplication.data.remote.order.AddOrdersBody
import com.example.getfixapplication.databinding.ActivityDetailOrderBinding
import com.example.getfixapplication.ui.booking.BookingViewModel
import com.example.getfixapplication.utils.ConstVal
import com.example.getfixapplication.utils.ConstVal.TEKNISI_FOTO
import com.example.getfixapplication.utils.ConstVal.TEKNISI_NAMA
import com.example.getfixapplication.utils.ConstVal.TEKNISI_RATING
import com.example.getfixapplication.utils.ConstVal.USER_DESC
import com.example.getfixapplication.utils.ConstVal.USER_JADWAL
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN
import com.example.getfixapplication.utils.ConstVal.USER_TANGGAL
import com.example.getfixapplication.utils.ConstVal.USER_TIPE_LAYANAN
import com.example.getfixapplication.utils.ConstVal.USER_WILAYAH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOrderActivity : AppCompatActivity() {

    private val detailorderVM: DetailOrderViewModel by viewModels()
    private val bookVM: BookingViewModel by viewModels()
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

            val request = AddOrdersBody(
                tanggal,
                jenisLayanan,
                namaTeknisi,
                waktu,
                wilayah,
                descLayanan,
                "bb12",
                "ad"
            )


        }


    }



//    private fun findRestaurant() {
//        val client = AppModule..getRestaurant(RESTAURANT_ID)
//        client.enqueue(object : Callback<RestaurantResponse> {
//            override fun onResponse(
//                call: Call<RestaurantResponse>,
//                response: Response<RestaurantResponse>
//            ) {
//                showLoading(false)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        setRestaurantData(responseBody.restaurant)
//                        setReviewData(responseBody.restaurant.customerReviews)
//                    }
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
//                showLoading(false)
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
//    }

    private fun setOrderDetail(orderItem: OrderItem) {
        binding.infoInvoice.text = orderItem.infoInvoice
        binding.tvDescNama.text = orderItem.nameTechnision
        binding.textView2.text = orderItem.id
        binding.tvAlamatLokasi.text = orderItem.alamat
        binding.tvTanggal.text=orderItem.tanggal
        binding.tvJam.text=orderItem.waktu
        Glide.with(this@DetailOrderActivity)
            .load(data)
            .into(binding.ivFoto)
    }

}