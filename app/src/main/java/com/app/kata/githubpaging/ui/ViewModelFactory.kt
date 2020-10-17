package com.app.kata.githubpaging.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.kata.githubpaging.ui.model.GithubProfileRepositoriesViewModel
import com.app.kata.githubpagingcore.data.GithubProfileRepository

class ViewModelFactory(private val repository: GithubProfileRepository) :
  ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {

    if (modelClass.isAssignableFrom(GithubProfileRepositoriesViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return GithubProfileRepositoriesViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
