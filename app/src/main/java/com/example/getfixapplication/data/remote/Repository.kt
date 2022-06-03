package com.example.getfixapplication.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.getfixapplication.data.remote.order.AddOrdersBody
import com.example.getfixapplication.data.remote.order.AddOrdersResponse
import com.example.getfixapplication.data.remote.order.OrdersSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val ordersSource: OrdersSource) {

    suspend fun addOrdersService(
        addOrdersBody: AddOrdersBody
    ): Flow<ApiResult<AddOrdersResponse>> {
        return ordersSource.addOrders(addOrdersBody).flowOn(Dispatchers.Default)
    }
}