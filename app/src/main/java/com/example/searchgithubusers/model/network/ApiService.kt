package com.example.searchgithubusers.model.network

import com.example.searchgithubusers.model.network.bean.SearchUsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/search/users")
    suspend fun searchUsers(
        @Query("q") queryData: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): SearchUsersResponse
}