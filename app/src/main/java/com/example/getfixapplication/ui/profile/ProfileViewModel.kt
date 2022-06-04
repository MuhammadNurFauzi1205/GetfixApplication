package com.example.getfixapplication.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getfixapplication.data.model.User
import com.example.getfixapplication.data.remote.ApiResult
import com.example.getfixapplication.data.remote.Repository
import com.example.getfixapplication.data.remote.order.AddOrdersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel  @Inject constructor(private val repository: Repository): ViewModel() {

    fun profileUser(userId : String) : LiveData<User>{
        val result = MutableLiveData<User>()

        viewModelScope.launch {
            val list = User(
                repository.getProfileUserService(userId).username,
                repository.getProfileUserService(userId).nama,
                repository.getProfileUserService(userId).email,
                repository.getProfileUserService(userId).password
            )
            result.value = list
        }
        return result
    }
}