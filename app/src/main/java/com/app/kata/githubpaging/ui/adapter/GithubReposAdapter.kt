package com.app.kata.githubpaging.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.kata.githubpaging.R
import com.app.kata.githubpaging.ui.model.GithubRepoUiModel

class GithubReposAdapter :
  PagingDataAdapter<GithubRepoUiModel, RecyclerView.ViewHolder>(GITHUB_VIEW_MODEL_COMPARATOR) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): RecyclerView.ViewHolder {
    return if (viewType == R.layout.github_repo_view_item) {
      GithubRepoViewHolder.create(parent)
    } else {
      GithubRepoSeparatorViewHolder.create(parent)
    }
  }

  override fun getItemViewType(position: Int): Int {
    return when (getItem(position)) {
      is GithubRepoUiModel.Item -> R.layout.github_repo_view_item
      is GithubRepoUiModel.SeparatorItem -> R.layout.github_repo_separator_view_item
      null -> throw UnsupportedOperationException("Unknown view")
    }
  }

  override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    position: Int
  ) {
    val uiModel = getItem(position)
    uiModel.let {
      when (uiModel) {
        is GithubRepoUiModel.Item ->
          (holder as GithubRepoViewHolder).bind(uiModel.githubRepo)
        is GithubRepoUiModel.SeparatorItem -> (holder as GithubRepoSeparatorViewHolder).bind(
          uiModel.description
        )
      }
    }
  }

  companion object {

    private val GITHUB_VIEW_MODEL_COMPARATOR =
      object : DiffUtil.ItemCallback<GithubRepoUiModel>() {
        override fun areItemsTheSame(
          oldItem: GithubRepoUiModel,
          newItem: GithubRepoUiModel
        ): Boolean {
          return (oldItem is GithubRepoUiModel.Item
              && newItem is GithubRepoUiModel.Item &&
              oldItem.githubRepo.fullName == newItem.githubRepo.fullName) ||
              (oldItem is GithubRepoUiModel.SeparatorItem &&
                  newItem is GithubRepoUiModel.SeparatorItem &&
                  oldItem.description == newItem.description)
        }

        override fun areContentsTheSame(
          oldItem: GithubRepoUiModel,
          newItem: GithubRepoUiModel
        ): Boolean =
          oldItem == newItem
      }
  }
}