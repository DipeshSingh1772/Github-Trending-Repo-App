package com.example.zoho_project.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.zoho_project.ApiManager.Api.ApiInterface
import com.example.zoho_project.ApiManager.Model.TrendingRepoItem
import com.example.zoho_project.RoomManager.RepoDatabase
import com.example.zoho_project.Utils.MyUtils

//we make repository for separation.
//inside repository we use apiInterface for calling all data resource, whereas outside we always use repository
//so by this we make separation viewmodel not directly interact with apiInterface.
class MainRepository(
    private val apiInterface: ApiInterface,
    private val repoDatabase: RepoDatabase,
    private val applicationContext: Context
) {

    //data from database offline
    private val repoListOffline = MutableLiveData<List<TrendingRepoItem>>()
    val reposListOffline:LiveData<List<TrendingRepoItem>>
    get() = repoListOffline

    //will get data when api is called
    private val repoList = MutableLiveData<List<TrendingRepoItem>>()
    val repos:LiveData<List<TrendingRepoItem>>
    get() = repoList

    //when this function is called from viewmodel init block the data will post on above repoList mutable live data
    // then we set that data into repos livedata which will access from any other activity or file.
    suspend fun getAllRepo(){
        //if internet available we fetch data and push into db
        if(MyUtils.isInternetAvailable(applicationContext) == true){
            val response = apiInterface.getAllRepo()
            if(!(response.body().isNullOrEmpty())){
                //get from api and insert into room database
                val data = response.body()!!
                repoDatabase.repoDao().deleteAllData()
                repoDatabase.repoDao().insertRepos(data)
                repoList.postValue(response.body())
            }
        }
    }

    suspend fun getAllOfflineRepo(){
        val data =  repoDatabase.repoDao().getAllRepoOffline()
        repoListOffline.postValue(data)
    }
}