package com.app.kata.githubpagingcore.data.source.api.model

import com.google.gson.annotations.SerializedName

data class GithubReposSearchResponse(
  @SerializedName("total_count") val total: Int = 0,
  @SerializedName("items") val items: List<GithubRepoDto> = emptyList(),
  val nextPage: Int? = null
)