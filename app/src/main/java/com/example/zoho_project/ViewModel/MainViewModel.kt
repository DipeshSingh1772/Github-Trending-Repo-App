package com.example.zoho_project.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zoho_project.ApiManager.Model.TrendingRepoItem
import com.example.zoho_project.Repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//for creating parameterized viewmodel we use viewmodel factory
class MainViewModel(private val repository: MainRepository) : ViewModel() {

    //when viewmodel is called init block will invoke and call for data from api from repository.
    init {
        //calling with coroutine because it is suspend funtion which works in background thread
        viewModelScope.launch (Dispatchers.IO){
            repository.getAllRepo()
        }
    }

    //access from mainActivity and get online data from repository
    val allRepos:LiveData<List<TrendingRepoItem>>
    get() = repository.repos

    //access from mainActivity and get offline data from repository
    val allOfflineRepos:LiveData<List<TrendingRepoItem>>
    get() = repository.reposListOffline


    //custom function for force fully calling function for get data from api
    fun refreshList() = viewModelScope.launch {
        repository.getAllRepo()
    }

    //custom function for force fully calling function for get data from database
    fun getAllOfflineData() = viewModelScope.launch {
        repository.getAllOfflineRepo()
    }
}