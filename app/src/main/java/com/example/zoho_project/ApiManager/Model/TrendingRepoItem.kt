package com.example.zoho_project.ApiManager.Model

import androidx.annotation.NonNull
import androidx.room.*
import com.google.gson.Gson
import java.util.*


//we use same model for api call as well as room database

@Entity(tableName = "Trending_Repos")
data class TrendingRepoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val language :String,
    val languageColor:String,
    val stars:Int
)
