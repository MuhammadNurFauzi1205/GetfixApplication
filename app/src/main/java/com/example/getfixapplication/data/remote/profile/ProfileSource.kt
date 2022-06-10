package com.example.getfixapplication.data.remote.profile

import android.util.Log
import com.example.getfixapplication.data.model.OrderListItem
import com.example.getfixapplication.data.model.Teknisi
import com.example.getfixapplication.data.model.User
import com.example.getfixapplication.data.remote.ApiResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileSource @Inject constructor (private val firestore: FirebaseFirestore, private val profileService: ProfileService) {

    suspend fun getProfileDataUser(userId: String): User {
        val user = firestore.collection("users").document(userId).get().await()
        user.get("nama")?.toString()?.let {
            return User(
                user.get("username").toString(),
                user.get("nama").toString(),
                user.get("email").toString(),
                user.get("password").toString())
        }
        // return datasnapshot to user
        return user.toObject(User::class.java)!!
    }

    // Create get Teknisi Data Flow
    suspend fun getProfileDataTeknisi(userId : String): Flow<ApiResult<Teknisi>> {
        return flow {
            try {
                emit(ApiResult.loading())
                val response = profileService.getTeknisiData(userId)
                if (response.username.isEmpty()) {
                    emit(ApiResult.success(response))
                } else {
                    emit(ApiResult.error("User tidak ada"))
                }
            } catch (ex: Exception) {
                emit(ApiResult.error(ex.message.toString()))
            }
        }
    }

    // Create get Teknisi
    suspend fun getListTeknsisi(layanan : String, area : String): Flow<ApiResult<List<Teknisi>>> {
        return flow {
            try {
                emit(ApiResult.loading())
                val response = profileService.getTeknisiList(layanan, area)
                if (response.isNotEmpty()) {
                    emit(ApiResult.success(response))
                } else {
                    emit(ApiResult.error("User tidak ada"))
                }
            } catch (ex: Exception) {
                emit(ApiResult.error(ex.message.toString()))
            }
        }
    }


}