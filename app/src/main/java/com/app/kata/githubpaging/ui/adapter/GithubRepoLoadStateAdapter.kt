package com.app.kata.githubpaging.ui.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class GithubRepoLoadStateAdapter(private val retry: () -> Unit) :
  LoadStateAdapter<GithubRepoLoadStateViewHolder>() {

  override fun onBindViewHolder(holder: GithubRepoLoadStateViewHolder, loadState: LoadState) {
    holder.bind(loadState)
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    loadState: LoadState
  ) = GithubRepoLoadStateViewHolder.create(parent, retry)
}