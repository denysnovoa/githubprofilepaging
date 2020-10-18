package com.app.kata.githubpaging.ui.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class GithubProfileLoadStateAdapter(private val retry: () -> Unit) :
  LoadStateAdapter<GithubProfileLoadStateViewHolder>() {

  override fun onBindViewHolder(holder: GithubProfileLoadStateViewHolder, loadState: LoadState) {
    holder.bind(loadState)
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    loadState: LoadState
  ) = GithubProfileLoadStateViewHolder.create(parent, retry)
}