package com.example.getfixapplication.utils

import android.Manifest

object ConstVal {
    const val timeSplash: Int = 3000
    const val FILENAME_FORMAT = "dd-MMM-yyyy"
    const val CAMERA_X_RESULT = 200
    var PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}