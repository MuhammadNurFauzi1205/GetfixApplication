package com.example.getfixapplication.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getfixapplication.data.model.Teknisi
import com.example.getfixapplication.data.remote.ApiResult
import com.example.getfixapplication.data.remote.Repository
import com.example.getfixapplication.data.remote.order.AddOrdersResponse
import com.example.getfixapplication.data.remote.order.OrdersBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun addOrdersService(addOrdersBody: OrdersBody): LiveData<ApiResult<AddOrdersResponse>> {
        val result = MutableLiveData<ApiResult<AddOrdersResponse>>()
        viewModelScope.launch {
            repository.addOrdersService(addOrdersBody).collect {
                result.postValue(it)
            }
        }
        return result
    }

    fun getTeknisiService(layanan: String, area: String): LiveData<ApiResult<List<Teknisi>>> {
        val result = MutableLiveData<ApiResult<List<Teknisi>>>()
        viewModelScope.launch {
            repository.getListTeknisiService(layanan, area).collect {
                result.postValue(it)
            }
        }
        return result
    }

}