package com.example.getfixapplication.data.remote.order

import com.example.getfixapplication.data.model.OrderItem
import com.example.getfixapplication.data.model.User
import com.example.getfixapplication.data.remote.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderItemSource @Inject constructor(
    private val ordersService: OrdersService
) {

//    suspend fun getProfileData(userId: String): User {
//        val user = firestore.collection("users").document(userId).get().await()
//        user.get("nama")?.toString()?.let {
//            return User(user.get("username").toString(), user.get("nama").toString(), user.get("email").toString(), user.get("password").toString())
//        }
//        // return datasnapshot to user
//        return user.toObject(User::class.java)!!
//    }
}