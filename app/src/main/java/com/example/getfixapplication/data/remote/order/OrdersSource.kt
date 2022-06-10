package com.example.getfixapplication.data.remote.order

import com.example.getfixapplication.data.model.OrderListItem
import com.example.getfixapplication.data.remote.ApiResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrdersSource @Inject constructor(
    private val ordersService: OrdersService,
    private val firestore: FirebaseFirestore,
) {

    suspend fun addOrders(
        addOrdersBody: OrdersBody
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

    // Create get All Orders Flow
    suspend fun getListOrders(userId: String, type: Int): Flow<ApiResult<List<OrderListItem>>> {
        return flow {
            try {
                emit(ApiResult.loading())
                val user = firestore.collection("users").document(userId).get().await()
                user.get("username").toString()
                val filtering: List<OrderListItem>
                val response = ordersService.getAllOrders(user.data?.get("username") as String)

                filtering = if (type == 0) {
                    response.filter { it.statusOrder == "Pesanan Success" }
                } else if (type == 1) {
                    response.filter { it.statusOrder == "Pesanan Success" }
                } else {
                    response
                }

                if (filtering.isNotEmpty()) {
                    emit(ApiResult.success(filtering))
                } else {
                    emit(ApiResult.error("Tidak Ada Pesanan"))
                }
            } catch (ex: Exception) {
                emit(ApiResult.error(ex.message.toString()))
            }
        }
    }

    //get orders by id
    suspend fun getOrdersId(
        orderId: String
    ): Flow<ApiResult<OrdersBody>> {
        return flow {
            try {
                emit(ApiResult.loading())
                val response = ordersService.getOrders(
                    orderId
                )
                if (response.userTeknisi?.isEmpty() == false) {
                    emit(ApiResult.success(response))
                } else {
                    emit(ApiResult.error("Tidak Ada Data"))
                }
            } catch (ex: Exception) {
                emit(ApiResult.error(ex.message.toString()))
            }
        }
    }
}