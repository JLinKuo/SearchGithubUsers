package com.example.searchgithubusers.view.SearchUsers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.searchgithubusers.model.network.Resource
import com.example.searchgithubusers.model.network.bean.SearchUsersResponse
import com.example.searchgithubusers.model.repository.SearchUsersRepository
import com.example.searchgithubusers.view.base.BaseViewModel
import kotlinx.coroutines.launch

class SearchUsersViewModel: BaseViewModel() {
    private val repository by lazy { SearchUsersRepository() }

    var nextPage = 1    // 預設第一頁
    var isLoading = false

    private val _searchUsersResponse = MutableLiveData<Resource<SearchUsersResponse>>()
    val searchUsersRepository: LiveData<Resource<SearchUsersResponse>>
        get() = _searchUsersResponse

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _searchUsersResponse.value = Resource.Loading
            _searchUsersResponse.value = repository.searchUsers(query, nextPage)
        }
    }
}