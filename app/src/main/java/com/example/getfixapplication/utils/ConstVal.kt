package com.example.getfixapplication.utils

import android.Manifest

object ConstVal {
    const val timeSplash: Int = 3000
    const val FILENAME_FORMAT = "dd-MMM-yyyy"
    const val CAMERA_X_RESULT = 200
    const val USER_JADWAL = "EX_JADWAL"
    const val USER_TANGGAL = "EX_TANGGAL"
    const val USER_DESC = "EX_DESC"
    const val USER_LAYANAN = "EX_LAYANAN"
    const val USER_JENIS_TUGAS = "EX_JENIS_TUGAS"
    const val USER_WILAYAH = "EX_WILAYAH"
    const val USER_ALAMAT = "EX_STATUS"
    const val USER_ID_SESSION = "EX_ID_SESSION"
    const val TEKNISI_RATING = "EX_RATING"
    const val TEKNISI_FOTO = "EX_TEKNISI_FOTO"
    const val USERNAME = "EX_USERNAME"
    const val ORDER_ID  = "EX_ORDER_ID"
    const val TEKNISI_NAMA = "EX_NAMA_TEKNISI"
    const val RC_SIGN_IN = 1001
    var PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}