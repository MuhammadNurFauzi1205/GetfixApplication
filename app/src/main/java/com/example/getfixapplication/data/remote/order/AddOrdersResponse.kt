package com.example.getfixapplication.data.remote.order

import com.google.gson.annotations.SerializedName

data class AddOrdersResponse(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class Data(

    @field:SerializedName("pesanan")
    val pesanan: Pesanan? = null
)

data class Pesanan(

    @field:SerializedName("jadwal")
    val jadwal: String? = null,

    @field:SerializedName("keterangan")
    val keterangan: String? = null,

    @field:SerializedName("layanan")
    val layanan: String? = null,

    @field:SerializedName("userTeknisi")
    val userTeknisi: String? = null,

    @field:SerializedName("waktu")
    val waktu: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("wilayah")
    val wilayah: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null
)
