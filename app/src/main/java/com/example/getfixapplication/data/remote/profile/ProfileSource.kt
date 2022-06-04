package com.example.getfixapplication.data.remote.profile

import android.util.Log
import com.example.getfixapplication.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileSource @Inject constructor (private val firestore: FirebaseFirestore) {

    suspend fun getProfileData(userId: String): User {
        val user = firestore.collection("users").document(userId).get().await()
        user.get("nama")?.toString()?.let {
            return User(user.get("username").toString(), user.get("nama").toString(), user.get("email").toString(), user.get("password").toString())
        }
        // return datasnapshot to user
        return user.toObject(User::class.java)!!
    }


}