package com.example.searchgithubusers.model.repository

import com.example.searchgithubusers.model.network.ApiService
import com.example.searchgithubusers.model.network.RemoteDataSource
import com.example.searchgithubusers.model.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

abstract class BaseRepository {
    protected val apiService by lazy { RemoteDataSource().buildApi(ApiService::class.java) }

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(
                            isNetworkErr = true,
                            errorCode = throwable.code(),
                            errorBody = throwable.response()?.errorBody(),
                            throwable = throwable
                        )
                    }

                    is IOException -> {
                        Resource.Failure(
                            isNetworkErr = false,
                            errorCode = null,
                            errorBody = null,
                            throwable = throwable
                        )
                    }

                    else -> {
                        Resource.Failure(
                            isNetworkErr = false,
                            errorCode = null,
                            errorBody = null,
                            throwable = throwable
                        )
                    }
                }
            }
        }
    }
}