package com.example.getfixapplication.data.remote.order

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

}