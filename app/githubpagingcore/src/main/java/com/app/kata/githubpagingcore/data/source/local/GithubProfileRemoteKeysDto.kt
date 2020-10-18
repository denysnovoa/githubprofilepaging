package com.app.kata.githubpagingcore.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_profile_remote_keys")
class GithubProfileRemoteKeysDto(
  @PrimaryKey
  val githubProfileId: Long,
  val prevKey: Int?,
  val nextKey: Int?
)