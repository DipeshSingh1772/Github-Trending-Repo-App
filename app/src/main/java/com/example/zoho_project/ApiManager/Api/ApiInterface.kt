package com.example.zoho_project.ApiManager.Api

import com.example.zoho_project.ApiManager.Model.TrendingRepoItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/repositories")
    suspend fun getAllRepo() : Response<List<TrendingRepoItem>>
}