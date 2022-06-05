package com.example.getfixapplication.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getfixapplication.data.model.OrderListItem
import com.example.getfixapplication.data.remote.ApiResult
import com.example.getfixapplication.data.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOrderViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getOrdersService(userId : String): LiveData<ApiResult<List<OrderListItem>>> {
        val result = MutableLiveData<ApiResult<List<OrderListItem>>>()
        viewModelScope.launch {
            repository.getListOrderItemService(userId).collect {
                result.postValue(it)
            }
        }
        return result
    }

}