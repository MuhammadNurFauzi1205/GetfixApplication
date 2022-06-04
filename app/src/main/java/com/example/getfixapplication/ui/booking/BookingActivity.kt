package com.example.getfixapplication.ui.booking

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.getfixapplication.R
import com.example.getfixapplication.databinding.ActivityBookingBinding
import com.example.getfixapplication.ui.camera.CameraActivity
import com.example.getfixapplication.utils.ConstVal.CAMERA_X_RESULT
import com.example.getfixapplication.utils.ConstVal.USER_DESC
import com.example.getfixapplication.utils.ConstVal.USER_JADWAL
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN
import com.example.getfixapplication.utils.ConstVal.USER_TANGGAL
import com.example.getfixapplication.utils.ConstVal.USER_TIPE_LAYANAN
import com.example.getfixapplication.utils.ConstVal.USER_WILAYAH
import com.example.getfixapplication.utils.rotateBitmap
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.withTimeoutOrNull
import java.io.File

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = resources.getStringArray(R.array.data_jenis_layanan)
        val adapterDropdown = ArrayAdapter(this, R.layout.drowdown_item, data)
        binding.jenisTugas.setAdapter(adapterDropdown)

        val dataKota = resources.getStringArray(R.array.data_kota)
        val adapterDropdownKota = ArrayAdapter(this, R.layout.drowdown_item, dataKota)
        binding.alamatDrop.setAdapter(adapterDropdownKota)

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

        //Create Date picker
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()

        // click navigation item toolbar to back
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        binding.ivCalender.setOnClickListener {
            picker.show(supportFragmentManager, picker.toString())
        }

        picker.addOnPositiveButtonClickListener {
            binding.tvTanggal.text = picker.headerText
        }

        //create Time picker
        val pickerTime =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Pilih Waktu Temu")
                .build()

        binding.ivClock.setOnClickListener {
            pickerTime.show(supportFragmentManager, pickerTime.toString())
        }

        pickerTime.addOnPositiveButtonClickListener {
            binding.tvWaktu.text = pickerTime.hour.toString() + ":" + pickerTime.minute.toString()
        }

        //move from booking to pilih teknisi
        binding.btnNext.setOnClickListener {
            val wilayah = binding.alamatDrop.text.toString()
            val descLayanan = binding.descTugas.text.toString()
            val jenisTugas = binding.jenisTugas.text.toString()

            val intent = Intent(this, PilihTeknisiActivity::class.java)
            intent.putExtra(USER_DESC, descLayanan)
            intent.putExtra(USER_TANGGAL, binding.tvTanggal.text)
            intent.putExtra(USER_JADWAL, binding.tvWaktu.text)
            intent.putExtra(USER_LAYANAN, binding.jenislayanan.text)
            intent.putExtra(USER_TIPE_LAYANAN, jenisTugas)
            intent.putExtra(USER_WILAYAH, wilayah)
            startActivity(intent)
        }

    }
}