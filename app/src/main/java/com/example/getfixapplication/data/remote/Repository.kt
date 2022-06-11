package com.example.getfixapplication.data.remote

import com.example.getfixapplication.data.model.OrderListItem
import com.example.getfixapplication.data.model.Teknisi
import com.example.getfixapplication.data.model.User
import com.example.getfixapplication.data.remote.order.*
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
        return profileSource.getProfileDataUser(userId)
    }

    suspend fun getProfileTeknisiService(userId: String): Flow<ApiResult<Teknisi>> {
        return profileSource.getProfileDataTeknisi(userId).flowOn(Dispatchers.Default)
    }

    suspend fun getListTeknisiService(layanan: String, area: String): Flow<ApiResult<List<Teknisi>>> {
        return profileSource.getListTeknisi(layanan, area).flowOn(Dispatchers.Default)
    }

    suspend fun getListOrderItemService(userId: String, type: Int): Flow<ApiResult<List<OrderListItem>>> {
        return ordersSource.getListOrders(userId, type).flowOn(Dispatchers.Default)
    }

    suspend fun getOrderService(token: String, orderId: String): Flow<ApiResult<OrdersBody>> {
        return ordersSource.getOrdersId(token, orderId).flowOn(Dispatchers.Default)
    }

    suspend fun updateStatusOrderService(status: StatusOrderBody, orderId: String): Flow<ApiResult<StatusOrderResponse>> {
        return ordersSource.updateStatusOrders(status, orderId).flowOn(Dispatchers.Default)
    }
}