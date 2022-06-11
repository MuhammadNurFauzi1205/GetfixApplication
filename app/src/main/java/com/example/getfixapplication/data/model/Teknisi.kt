package com.example.getfixapplication.data.model

import com.google.gson.annotations.SerializedName

data class Teknisi(
    val nama: String,
    val daerah_user: String,
    val username: String,
    val email: String,
    val layanan: String,
    @field:SerializedName("image")
    val foto: String,
    val rating: Float
)