package com.app.kata.githubpagingcore.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_repos_remote_keys")
class GithubRepoRemoteKeysDto(
  @PrimaryKey
  val githubRepoId: Long,
  val prevKey: Int?,
  val nextKey: Int?
)