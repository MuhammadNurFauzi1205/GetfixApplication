package com.example.getfixapplication.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getfixapplication.data.model.OrderItem
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


    fun addOrdersService(addOrdersBody: OrderItem): LiveData<ApiResult<AddOrdersResponse>> {
        val result = MutableLiveData<ApiResult<AddOrdersResponse>>()
        viewModelScope.launch {
            repository.getOrderService(addOrdersBody).collect {
                result.postValue(it)
            }
        }
        return result
    }

}