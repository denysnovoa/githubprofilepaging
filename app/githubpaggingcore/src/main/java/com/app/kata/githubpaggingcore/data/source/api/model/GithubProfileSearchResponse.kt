package com.app.kata.githubpaggingcore.data.source.api.model

import com.google.gson.annotations.SerializedName

data class GithubProfileSearchResponse(
  @SerializedName("total_count") val total: Int = 0,
  @SerializedName("items") val items: List<GithubProfileDto> = emptyList(),
  val nextPage: Int? = null
)