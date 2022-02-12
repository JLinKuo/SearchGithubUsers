package com.example.searchgithubusers.model.repository

class SearchUsersRepository: BaseRepository() {
    suspend fun searchUsers() = safeApiCall {
        apiService.searchUsers("qoo", 30, 1)
    }
}