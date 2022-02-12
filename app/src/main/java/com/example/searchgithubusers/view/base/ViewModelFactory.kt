package com.example.searchgithubusers.view.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.searchgithubusers.view.SearchUsers.SearchUsersViewModel
import com.example.searchgithubusers.view.main.MainViewModel

class ViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // Activity
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel() as T
            }

            // All Fragments
            modelClass.isAssignableFrom(SearchUsersViewModel::class.java) -> {
                SearchUsersViewModel() as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel Class.")
        }
    }
}