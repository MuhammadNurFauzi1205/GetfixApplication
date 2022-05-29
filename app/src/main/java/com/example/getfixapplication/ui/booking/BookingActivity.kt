package com.example.getfixapplication.ui.booking

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.getfixapplication.R
import com.example.getfixapplication.databinding.ActivityBookingBinding
import com.example.getfixapplication.ui.camera.CameraActivity
import com.example.getfixapplication.utils.ConstVal.CAMERA_X_RESULT
import com.example.getfixapplication.utils.rotateBitmap
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
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

        //Create Date picker
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
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

    }
}