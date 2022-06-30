package com.example.zoho_project.RoomManager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zoho_project.ApiManager.Model.TrendingRepoItem

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(item:List<TrendingRepoItem>)

    @Query("SELECT * FROM Trending_Repos")
    suspend fun getAllRepoOffline() : List<TrendingRepoItem>

    @Query("DELETE FROM Trending_Repos")
    suspend fun deleteAllData()
}