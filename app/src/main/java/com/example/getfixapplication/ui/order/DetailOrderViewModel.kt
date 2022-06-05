package com.example.getfixapplication.ui.order

import androidx.lifecycle.ViewModel
import com.example.getfixapplication.data.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
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