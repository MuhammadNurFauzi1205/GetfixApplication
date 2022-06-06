package com.example.getfixapplication.data.remote

import com.example.getfixapplication.data.model.OrderListItem
import com.example.getfixapplication.data.model.User
import com.example.getfixapplication.data.remote.order.OrdersBody
import com.example.getfixapplication.data.remote.order.AddOrdersResponse
import com.example.getfixapplication.data.remote.order.OrdersSource
import com.example.getfixapplication.data.remote.profile.ProfileSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor
    (private val ordersSource: OrdersSource, private val profileSource: ProfileSource)
{

    suspend fun addOrdersService(
        addOrdersBody: OrdersBody
    ): Flow<ApiResult<AddOrdersResponse>> {
        return ordersSource.addOrders(addOrdersBody).flowOn(Dispatchers.Default)
    }

    suspend fun getProfileUserService(userId: String): User {
        return profileSource.getProfileData(userId)
    }

    suspend fun getListOrderItemService(userId : String): Flow<ApiResult<List<OrderListItem>>> {
        return ordersSource.getListOrders(userId).flowOn(Dispatchers.Default)
    }

    suspend fun getOrderService(orderId: String): Flow<ApiResult<OrdersBody>> {
        return ordersSource.getOrdersId(orderId).flowOn(Dispatchers.Default)
    }
}