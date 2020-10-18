package com.app.kata.githubpaging.ui.model

import com.app.kata.githubpagingcore.data.source.api.model.GithubProfileDto

sealed class GithubProfileUiModel {

  data class Item(val githubProfile: GithubProfileDto) : GithubProfileUiModel() {

    internal val roundedStarCount: Int
      get() = this.githubProfile.stars / 10_000
  }

  data class SeparatorItem(val description: String) : GithubProfileUiModel()
}