package com.app.kata.githubpagingcore.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kata.githubpagingcore.data.source.api.model.GithubProfileDto

@Dao
interface GithubProfileDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(repos: List<GithubProfileDto>)

  @Query(
    "SELECT * FROM github_profiles WHERE " +
        "name LIKE :queryString OR description LIKE :queryString " +
        "ORDER BY stars DESC, name ASC"
  )
  fun reposByName(queryString: String): PagingSource<Int, GithubProfileDto>

  @Query("DELETE FROM github_profiles")
  suspend fun clearRepos()
}