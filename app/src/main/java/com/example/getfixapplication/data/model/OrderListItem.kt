package com.example.getfixapplication.data.model

import com.google.gson.annotations.SerializedName

data class OrderListItem(
    @field:SerializedName("id")
    val orderId: String,

    @field:SerializedName("layanan")
    val jenisOrder: String,

    @field:SerializedName("jadwal")
    val tanggalOrder: String,

    @field:SerializedName("keterangan")
    val statusOrder: String,

    val fotoUser: String
)