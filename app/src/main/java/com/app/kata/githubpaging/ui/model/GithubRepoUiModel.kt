package com.app.kata.githubpaging.ui.model

import com.app.kata.githubpagingcore.data.source.api.model.GithubRepoDto

sealed class GithubRepoUiModel {

  data class Item(val githubRepo: GithubRepoDto) : GithubRepoUiModel() {

    internal val roundedStarCount: Int
      get() = this.githubRepo.stars / 10_000
  }

  data class SeparatorItem(val description: String) : GithubRepoUiModel()
}