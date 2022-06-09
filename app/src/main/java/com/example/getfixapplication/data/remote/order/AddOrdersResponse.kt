package com.example.getfixapplication.data.remote.order

import com.example.getfixapplication.data.model.OrderListItem
import com.example.getfixapplication.data.model.Teknisi
import com.google.gson.annotations.SerializedName

data class AddOrdersResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: List<OrderListItem>
)
