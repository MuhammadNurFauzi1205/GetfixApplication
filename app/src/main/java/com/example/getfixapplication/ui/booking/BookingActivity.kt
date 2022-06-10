package com.example.getfixapplication.ui.booking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.getfixapplication.R
import com.example.getfixapplication.databinding.ActivityBookingBinding
import com.example.getfixapplication.utils.ConstVal.USER_ALAMAT
import com.example.getfixapplication.utils.ConstVal.USER_DESC
import com.example.getfixapplication.utils.ConstVal.USER_JADWAL
import com.example.getfixapplication.utils.ConstVal.USER_LAYANAN
import com.example.getfixapplication.utils.ConstVal.USER_TANGGAL
import com.example.getfixapplication.utils.ConstVal.USER_JENIS_TUGAS
import com.example.getfixapplication.utils.ConstVal.USER_WILAYAH
import com.example.getfixapplication.utils.showToast
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

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
            binding.tvWaktu.text = StringBuilder(pickerTime.hour.toString()).append(":" + pickerTime.minute.toString())
        }

        //move from booking to pilih teknisi
        binding.btnNext.setOnClickListener {
            // check if field is empty
            val wilayah = binding.alamatDrop.text.toString()
            val descLayanan = binding.descTugas.text.toString()
            val jenisTugas = binding.jenisTugas.text.toString()
            val alamat = binding.edtBookingAlamat.editText?.text.toString()

            if (binding.edtAlamatAnda.text.isEmpty() || binding.descTugas.text.isNullOrEmpty() || jenisTugas == "Pilih Layanan" || wilayah == "Pilih Area" || binding.tvTanggal.text == "Tanggal" || binding.tvWaktu.text == "Waktu") {
                showToast(this, "Lengkapi Data")
            } else {
                val intent = Intent(this, PilihTeknisiActivity::class.java)
                intent.putExtra(USER_DESC, descLayanan)
                intent.putExtra(USER_TANGGAL, binding.tvTanggal.text)
                intent.putExtra(USER_JADWAL, binding.tvWaktu.text)
                intent.putExtra(USER_LAYANAN, binding.jenislayanan.text)
                intent.putExtra(USER_JENIS_TUGAS, jenisTugas)
                intent.putExtra(USER_WILAYAH, wilayah)
                intent.putExtra(USER_ALAMAT, alamat)
                startActivity(intent)
            }
        }

    }
}