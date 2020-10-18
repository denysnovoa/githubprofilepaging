package com.app.kata.githubpaging.di

import com.app.kata.githubpaging.ui.ViewModelFactory
import com.app.kata.githubpagingcore.data.GithubReposRepository
import com.app.kata.githubpagingcore.data.source.api.GithubReposApiService

object Injection {

  private fun provideGithubRepository() = GithubReposRepository(GithubReposApiService.create())

  fun provideViewModelFactory() = ViewModelFactory(provideGithubRepository())
}