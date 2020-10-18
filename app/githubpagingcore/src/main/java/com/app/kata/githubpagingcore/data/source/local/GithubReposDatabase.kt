package com.app.kata.githubpagingcore.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.kata.githubpagingcore.data.source.api.model.GithubRepoDto

@Database(
  entities = [GithubRepoDto::class, GithubRepoRemoteKeysDto::class],
  version = 1,
  exportSchema = false
)
abstract class GithubReposDatabase : RoomDatabase() {

  abstract fun githubReposDao(): GithubReposDao
  abstract fun remoteKeysDao(): GithubRepoRemoteKeysDao

  companion object {

    @Volatile
    private var INSTANCE: GithubReposDatabase? = null

    fun getInstance(context: Context): GithubReposDatabase =
      INSTANCE ?: synchronized(this) {
        INSTANCE
          ?: buildDatabase(context).also { INSTANCE = it }
      }

    private fun buildDatabase(context: Context) =
      Room.databaseBuilder(
        context.applicationContext,
        GithubReposDatabase::class.java, "Github.db"
      ).build()
  }
}