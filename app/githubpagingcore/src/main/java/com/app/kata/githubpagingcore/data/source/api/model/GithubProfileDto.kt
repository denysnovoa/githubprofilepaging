package com.app.kata.githubpagingcore.data.source.api.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "github_profiles")
data class GithubProfileDto(
  @field:SerializedName("id") val id: Long,
  @field:SerializedName("name") val name: String,
  @field:SerializedName("full_name") val fullName: String,
  @field:SerializedName("description") val description: String?,
  @field:SerializedName("html_url") val url: String,
  @field:SerializedName("stargazers_count") val stars: Int,
  @field:SerializedName("forks_count") val forks: Int,
  @field:SerializedName("language") val language: String?
)