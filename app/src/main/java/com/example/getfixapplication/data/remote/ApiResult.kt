package com.example.getfixapplication.data.remote

import com.example.getfixapplication.utils.Status

data class ApiResult<T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ApiResult<T> = ApiResult(Status.SUCCESS, data, null)

        fun <T> error(msg: String?): ApiResult<T> = ApiResult(Status.ERROR, null, msg)

        fun <T> loading(): ApiResult<T> = ApiResult(Status.LOADING, null, null)
    }
}
