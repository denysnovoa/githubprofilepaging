package com.app.kata.githubpaging.di

import com.app.kata.githubpaging.ui.ViewModelFactory
import com.app.kata.githubpagingcore.data.GithubProfileRepository
import com.app.kata.githubpagingcore.data.source.api.GithubPagingApiService

object Injection {

  private fun provideGithubRepository() = GithubProfileRepository(GithubPagingApiService.create())

  fun provideViewModelFactory() = ViewModelFactory(provideGithubRepository())
}