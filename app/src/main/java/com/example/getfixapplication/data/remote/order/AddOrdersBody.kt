package com.example.getfixapplication.data.remote.order

import com.google.gson.annotations.SerializedName

data class AddOrdersBody(

	@field:SerializedName("jadwal")
	val jadwal: String? = null,

	@field:SerializedName("layanan")
	val layanan: String? = null,

	@field:SerializedName("teknisi")
	val teknisi: String? = null,

	@field:SerializedName("waktu")
	val waktu: String? = null,

	@field:SerializedName("wilayah")
	val wilayah: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
