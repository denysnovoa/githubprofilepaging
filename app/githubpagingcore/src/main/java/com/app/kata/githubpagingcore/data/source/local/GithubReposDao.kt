package com.app.kata.githubpagingcore.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.kata.githubpagingcore.data.source.api.model.GithubRepoDto

@Dao
interface GithubReposDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(repos: List<GithubRepoDto>)

  @Query(
    "SELECT * FROM github_repos WHERE " +
        "name LIKE :queryString OR description LIKE :queryString " +
        "ORDER BY stars DESC, name ASC"
  )
  fun profilesByName(queryString: String): PagingSource<Int, GithubRepoDto>

  @Query("DELETE FROM github_repos")
  suspend fun clearProfiles()
}