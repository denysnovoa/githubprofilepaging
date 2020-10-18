package com.app.kata.githubpaging.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.kata.githubpaging.R
import com.app.kata.githubpaging.ui.model.GithubProfileUiModel
import com.app.kata.githubpagingcore.data.source.api.model.GithubProfileDto

class GithubProfileAdapter :
  PagingDataAdapter<GithubProfileUiModel, RecyclerView.ViewHolder>(GITHUB_VIEW_MODEL_COMPARATOR) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): RecyclerView.ViewHolder {
    return if (viewType == R.layout.github_profile_view_item) {
      GithubProfileViewHolder.create(parent)
    } else {
      GithubProfileSeparatorViewHolder.create(parent)
    }
  }

  override fun getItemViewType(position: Int): Int {
    return when (getItem(position)) {
      is GithubProfileUiModel.Item -> R.layout.github_profile_view_item
      is GithubProfileUiModel.SeparatorItem -> R.layout.github_profile_separator_view_item
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
        is GithubProfileUiModel.Item ->
          (holder as GithubProfileViewHolder).bind(uiModel.githubProfile)
        is GithubProfileUiModel.SeparatorItem -> (holder as GithubProfileSeparatorViewHolder).bind(
          uiModel.description
        )
      }
    }
  }

  companion object {

    private val GITHUB_VIEW_MODEL_COMPARATOR =
      object : DiffUtil.ItemCallback<GithubProfileUiModel>() {
        override fun areItemsTheSame(
          oldItem: GithubProfileUiModel,
          newItem: GithubProfileUiModel
        ): Boolean {
          return (oldItem is GithubProfileUiModel.Item
              && newItem is GithubProfileUiModel.Item &&
              oldItem.githubProfile.fullName == newItem.githubProfile.fullName) ||
              (oldItem is GithubProfileUiModel.SeparatorItem &&
                  newItem is GithubProfileUiModel.SeparatorItem &&
                  oldItem.description == newItem.description)
        }

        override fun areContentsTheSame(
          oldItem: GithubProfileUiModel,
          newItem: GithubProfileUiModel
        ): Boolean =
          oldItem == newItem
      }
  }
}