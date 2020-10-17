package com.app.kata.githubpaging.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.kata.githubpagingcore.data.source.api.model.GithubProfileDto

class GithubProfileAdapter :
  PagingDataAdapter<GithubProfileDto, RecyclerView.ViewHolder>(GITHUB_PROFILE_COMPARATOR) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): RecyclerView.ViewHolder {
    return GithubProfileViewHolder.create(parent)
  }

  override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    position: Int
  ) {
    getItem(position)?.let {
      (holder as GithubProfileViewHolder).bind(it)
    }
  }

  companion object {

    private val GITHUB_PROFILE_COMPARATOR = object : DiffUtil.ItemCallback<GithubProfileDto>() {
      override fun areItemsTheSame(oldItem: GithubProfileDto, newItem: GithubProfileDto): Boolean =
        oldItem.fullName == newItem.fullName

      override fun areContentsTheSame(
        oldItem: GithubProfileDto,
        newItem: GithubProfileDto
      ): Boolean = oldItem == newItem

    }
  }
}