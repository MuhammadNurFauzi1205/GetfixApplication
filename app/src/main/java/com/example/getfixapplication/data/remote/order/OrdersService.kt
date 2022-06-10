package com.example.getfixapplication.data.remote.order

import com.example.getfixapplication.data.model.OrderListItem
import retrofit2.http.*

interface OrdersService {

    @POST("orders")
    suspend fun addOrders(
        @Body addOrdersBody: OrdersBody
    ): AddOrdersResponse

    @GET("orders/{id}")
    suspend fun getOrders(
        @Header("Authorization") BearerToken: String,
        @Path ("id") id :String
    ): OrdersBody

    @GET("allorder/{id}")
    suspend fun getAllOrders(
        @Path ("id") username :String
    ): List<OrderListItem>

}