package com.example.zoho_project

import android.app.Application
import com.example.zoho_project.ApiManager.Api.ApiInterface
import com.example.zoho_project.ApiManager.Api.ApiService
import com.example.zoho_project.Repository.MainRepository
import com.example.zoho_project.RoomManager.RepoDatabase

class MyApplication():Application() {

    lateinit var repository: MainRepository
    override fun onCreate() {
        super.onCreate()

        // why we make instance of apiInterface?
        // from mainActivity we call-> ViewModel
        // viewModel wants repsitory which provided by -> ViewModelFactory
        // ViewModelFactory return -> repository as a parameter of viewModel
        // repository wants -> apiInterface
        // that's why we create apiInterface for passing it in repository class
        val apiInterface = ApiService.getInstance().create(ApiInterface::class.java)

        //we have to pass database instance also in viewmodel so we can insert data from repository
        //similarly as apiInterface process
        //for creation of database, database builder wants application context
        val database = RepoDatabase.getDatabaseInstance(applicationContext)

        //now when we get apiInterface we make an instance of Repository
        //we pass both parameter which are required to call and save data local
        repository = MainRepository(apiInterface, database,applicationContext)
    }
}