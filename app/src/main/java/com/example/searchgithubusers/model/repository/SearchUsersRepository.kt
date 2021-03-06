package com.example.searchgithubusers.model.repository

class SearchUsersRepository: BaseRepository() {
    suspend fun searchUsers(query: String, nextPage: Int) = safeApiCall {
        apiService.searchUsers(query, 30, nextPage)
    }
}