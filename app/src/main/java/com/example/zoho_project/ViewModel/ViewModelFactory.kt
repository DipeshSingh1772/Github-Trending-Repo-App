package com.example.zoho_project.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zoho_project.Repository.MainRepository

// we use viewmodel factory because we can't directly create parameterized viewmodel, so factory provide us a viewmodel with parameter
class ViewModelFactory(private val repository: MainRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}