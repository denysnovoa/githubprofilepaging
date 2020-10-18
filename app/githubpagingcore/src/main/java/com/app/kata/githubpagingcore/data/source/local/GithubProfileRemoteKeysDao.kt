package com.app.kata.githubpagingcore.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface GithubProfileRemoteKeysDao {

  @Insert(onConflict = REPLACE)
  suspend fun insertAll(remoteKey: List<GithubProfileRemoteKeys>)

  @Query("SELECT * FROM github_profile_remote_keys WHERE githubProfileId = :githubProfileId")
  suspend fun remoteKeysRepoId(githubProfileId: Long): GithubProfileRemoteKeys?

  @Query("DELETE FROM github_profile_remote_keys")
  suspend fun clearRemoteKeys()
}