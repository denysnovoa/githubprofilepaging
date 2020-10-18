package com.app.kata.githubpaging.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.app.kata.githubpaging.databinding.GithubRepoLoadStateFooterViewItemBinding

class GithubRepoLoadStateViewHolder(
  private val binding: GithubRepoLoadStateFooterViewItemBinding,
  retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

  init {
    binding.retryButton.setOnClickListener { retry.invoke() }
  }

  fun bind(loadState: LoadState) {
    if (loadState is LoadState.Error) {
      binding.errorMsg.text = loadState.error.localizedMessage
    }
    binding.progressBar.isVisible = loadState is LoadState.Loading
    binding.retryButton.isVisible = loadState !is LoadState.Loading
    binding.errorMsg.isVisible = loadState !is LoadState.Loading
  }

  companion object {

    fun create(parent: ViewGroup, retry: () -> Unit) =
      GithubRepoLoadStateViewHolder(
        GithubRepoLoadStateFooterViewItemBinding.inflate(
          LayoutInflater.from(parent.context),
          parent,
          false
        ), retry
      )
  }
}