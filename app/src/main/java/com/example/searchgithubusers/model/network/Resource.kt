package com.example.searchgithubusers.model.network

import okhttp3.ResponseBody

sealed class Resource<out T> {
    data class Success<out T>(val value: T): Resource<T>()
    data class Failure(
        val isNetworkErr: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val throwable: Throwable
    ): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}