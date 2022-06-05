package com.example.getfixapplication.data.remote.order

import com.example.getfixapplication.data.model.OrderListItem
import retrofit2.http.*

interface OrdersService {

    @POST("orders")
    suspend fun addOrders(
        @Body addOrdersBody: AddOrdersBody
    ): AddOrdersResponse

    @GET("orders/{id}")
    suspend fun getOrders(
        @Query ("id") id :String
    ): AddOrdersResponse

    @GET("allorder")
    suspend fun getAllOrders(
        @Query ("username") username :String
    ): List<OrderListItem>

}