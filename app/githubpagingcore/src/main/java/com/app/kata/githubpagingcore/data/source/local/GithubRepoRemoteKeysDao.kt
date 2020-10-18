package com.app.kata.githubpagingcore.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface GithubRepoRemoteKeysDao {

  @Insert(onConflict = REPLACE)
  suspend fun insertAll(remoteKey: List<GithubRepoRemoteKeysDto>)

  @Query("SELECT * FROM github_repos_remote_keys WHERE githubRepoId = :githubRepoId")
  suspend fun remoteKeysRepoId(githubRepoId: Long): GithubRepoRemoteKeysDto?

  @Query("DELETE FROM github_repos_remote_keys")
  suspend fun clearRemoteKeys()
}