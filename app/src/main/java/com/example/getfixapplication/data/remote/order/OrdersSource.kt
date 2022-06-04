package com.example.getfixapplication.data.remote.order

import com.example.getfixapplication.data.remote.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrdersSource @Inject constructor(
    private val ordersService: OrdersService
) {

    suspend fun addOrders(
        addOrdersBody: AddOrdersBody
    ): Flow<ApiResult<AddOrdersResponse>> {
        return flow {
            try {
                emit(ApiResult.loading())
                val response = ordersService.addOrders(
                    addOrdersBody
                )
                if (!response.message.isNullOrEmpty()) {
                    emit(ApiResult.success(response))
                } else {
                    emit(ApiResult.error(response.message))
                }
            } catch (ex: Exception) {
                emit(ApiResult.error(ex.message.toString()))
            }
        }
    }
}