package com.app.kata.githubpagingcore.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.kata.githubpagingcore.data.source.api.model.GithubProfileDto

@Database(
  entities = [GithubProfileDto::class, GithubProfileRemoteKeysDto::class],
  version = 1,
  exportSchema = false
)
abstract class GithubProfileDatabase : RoomDatabase() {

  abstract fun githubProfileDao(): GithubProfileDao
  abstract fun remoteKeyDao(): GithubProfileRemoteKeysDao

  companion object {

    @Volatile
    private var INSTANCE: GithubProfileDatabase? = null

    fun getInstance(context: Context): GithubProfileDatabase =
      INSTANCE ?: synchronized(this) {
        INSTANCE
          ?: buildDatabase(context).also { INSTANCE = it }
      }

    private fun buildDatabase(context: Context) =
      Room.databaseBuilder(
        context.applicationContext,
        GithubProfileDatabase::class.java, "Github.db"
      ).build()
  }
}