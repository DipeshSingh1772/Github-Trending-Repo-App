package com.example.zoho_project.ApiManager.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//creating service builder which provide
//this will use when we create apiInterface. we pass our interface and getInstance funtion will give us an instance of interface
//which we will use for calling api
object ApiService {
    val BASE_URL = "https://private-757089-githubtrendingapi.apiary-mock.com"

    fun getInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}