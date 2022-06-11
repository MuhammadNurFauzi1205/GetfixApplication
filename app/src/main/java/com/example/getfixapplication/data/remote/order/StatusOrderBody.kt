package com.example.getfixapplication.data.remote.order

import com.google.gson.annotations.SerializedName

data class StatusOrderBody(

    @field:SerializedName("keterangan")
    val keterangan: String? = null,
)
