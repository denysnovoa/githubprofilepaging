package com.app.kata.githubpaging.di

import android.content.Context
import com.app.kata.githubpaging.ui.ViewModelFactory
import com.app.kata.githubpagingcore.data.GithubReposRepository
import com.app.kata.githubpagingcore.data.source.api.GithubReposApiService
import com.app.kata.githubpagingcore.data.source.local.GithubReposDatabase

object Injection {

  private fun provideGithubRepository(context: Context) =
    GithubReposRepository(GithubReposApiService.create(), GithubReposDatabase.getInstance(context))

  fun provideViewModelFactory(context: Context) = ViewModelFactory(provideGithubRepository(context))
}