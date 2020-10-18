package com.app.kata.githubpaging.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.kata.githubpaging.ui.model.GithubRepoRepositoriesViewModel
import com.app.kata.githubpagingcore.data.GithubReposRepository

class ViewModelFactory(private val repository: GithubReposRepository) :
  ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {

    if (modelClass.isAssignableFrom(GithubRepoRepositoriesViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return GithubRepoRepositoriesViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
