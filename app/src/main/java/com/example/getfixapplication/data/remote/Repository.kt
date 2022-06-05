package com.example.getfixapplication.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.getfixapplication.data.model.OrderItem
import com.example.getfixapplication.data.model.OrderListItem
import com.example.getfixapplication.data.model.User
import com.example.getfixapplication.data.remote.order.AddOrdersBody
import com.example.getfixapplication.data.remote.order.AddOrdersResponse
import com.example.getfixapplication.data.remote.order.OrderItemSource
import com.example.getfixapplication.data.remote.order.OrdersSource
import com.example.getfixapplication.data.remote.profile.ProfileSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor
    (private val ordersSource: OrdersSource, private val profileSource: ProfileSource,
     private val orderItemSource: OrderItemSource)
{

    suspend fun addOrdersService(
        addOrdersBody: AddOrdersBody
    ): Flow<ApiResult<AddOrdersResponse>> {
        return ordersSource.addOrders(addOrdersBody).flowOn(Dispatchers.Default)
    }

    suspend fun getProfileUserService(userId: String): User {
        return profileSource.getProfileData(userId)
    }

    suspend fun getListOrderItemService(userId : String): Flow<ApiResult<List<OrderListItem>>> {
        return ordersSource.getListOrders(userId)
    }

//    suspend fun getOrderService(Id: String): OrderItem {
//        return orderItemSource.getOrders(Id)
//    }
}