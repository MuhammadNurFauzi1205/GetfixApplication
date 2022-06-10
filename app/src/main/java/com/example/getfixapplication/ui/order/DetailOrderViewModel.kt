package com.example.getfixapplication.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getfixapplication.data.model.Teknisi
import com.example.getfixapplication.data.model.User
import com.example.getfixapplication.data.remote.ApiResult
import com.example.getfixapplication.data.remote.Repository
import com.example.getfixapplication.data.remote.order.AddOrdersResponse
import com.example.getfixapplication.data.remote.order.OrdersBody
import com.example.getfixapplication.data.remote.profile.TeknisiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailOrderViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    fun getOrdersService(token: String, orderId: String): LiveData<ApiResult<OrdersBody>> {
        val result = MutableLiveData<ApiResult<OrdersBody>>()
        viewModelScope.launch {
            repository.getOrderService(token, orderId).collect {
                result.postValue(it)
            }
        }
        return result
    }

    fun getTeknisiService(orderId: String): LiveData<ApiResult<Teknisi>> {
        val result = MutableLiveData<ApiResult<Teknisi>>()
        viewModelScope.launch {
            repository.getProfileTeknisiService(orderId).collect {
                result.postValue(it)
            }
        }
        return result
    }

}