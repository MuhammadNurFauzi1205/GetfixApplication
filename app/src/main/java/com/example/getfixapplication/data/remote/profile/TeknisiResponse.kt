package com.example.getfixapplication.data.remote.profile

import com.google.gson.annotations.SerializedName

data class TeknisiResponse(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("layanan")
	val layanan: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("daerah_user")
	val daerahUser: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
