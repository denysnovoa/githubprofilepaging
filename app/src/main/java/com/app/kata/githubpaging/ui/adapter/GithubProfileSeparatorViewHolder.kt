package com.app.kata.githubpaging.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.kata.githubpaging.databinding.GithubProfileSeparatorViewItemBinding

class GithubProfileSeparatorViewHolder(
  private val binding: GithubProfileSeparatorViewItemBinding
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(separatorText: String) {
    binding.separatorDescription.text = separatorText
  }

  companion object {
    fun create(parent: ViewGroup): GithubProfileSeparatorViewHolder {
      return GithubProfileSeparatorViewHolder(
        GithubProfileSeparatorViewItemBinding.inflate(
          LayoutInflater.from(parent.context), parent, false
        )
      )
    }
  }
}