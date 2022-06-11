package com.example.getfixapplication.data.remote.order

import com.google.gson.annotations.SerializedName

data class StatusOrderResponse(
    @field:SerializedName("status")
    val message: String? = null
)