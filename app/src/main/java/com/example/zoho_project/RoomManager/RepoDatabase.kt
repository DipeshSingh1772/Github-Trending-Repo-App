package com.example.zoho_project.RoomManager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.zoho_project.ApiManager.Model.TrendingRepoItem

@Database(entities = [TrendingRepoItem::class], version = 1)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao():RepoDao
    //The companion objects can access private members of the class.
    companion object{
        private var INSTANCE: RepoDatabase? = null

        fun getDatabaseInstance(context:Context):RepoDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    RepoDatabase::class.java,
                    "Trending_Repos",
                ).build()
            }

            return INSTANCE!!
        }
    }
}