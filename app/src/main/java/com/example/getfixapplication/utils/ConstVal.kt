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
    const val USER_TIPE_LAYANAN = "EX_TIPE_LAYANAN"
    const val USER_WILAYAH = "EX_WILAYAH"
    const val USER_ID_SESSION = "EX_ID_SESSION"
    const val TEKNISI_RATING = "EX_RATING"
    const val TEKNISI_FOTO = "EX_TEKNISI_FOTO"
    const val USER_LAYANAN_GAMBAR = "EX_GAMBAR"

    const val TEKNISI_NAMA = "EX_NAMA_TEKNISI"
    const val RC_SIGN_IN = 1001
    var PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}