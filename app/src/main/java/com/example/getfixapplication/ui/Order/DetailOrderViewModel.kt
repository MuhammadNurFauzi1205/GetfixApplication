package com.example.getfixapplication.ui.Order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getfixapplication.data.model.OrderItem
import com.example.getfixapplication.data.model.User
import com.example.getfixapplication.data.remote.ApiResult
import com.example.getfixapplication.data.remote.Repository
import com.example.getfixapplication.data.remote.order.AddOrdersBody
import com.example.getfixapplication.data.remote.order.AddOrdersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailOrderViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


//    fun profileUser(userId : String) : LiveData<OrderItem>{
//        val result = MutableLiveData<OrderItem>()
//
//        viewModelScope.launch {
//            val list = OrderItem(
//                repository.getOrderService(userId).,
//                repository.getOrderService(userId).,
//                repository.getOrderService(userId).,
//                repository.getOrderService(userId).,
//                repository.getOrderService(userId).,
//                repository.getOrderService(userId).,
//                repository.getOrderService(userId).,
//            )
//            result.value = list
//        }
//        return result
//    }

}