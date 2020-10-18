package com.app.kata.githubpaging.ui.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.app.kata.githubpaging.R
import com.app.kata.githubpaging.databinding.GithubRepoViewItemBinding
import com.app.kata.githubpagingcore.data.source.api.model.GithubRepoDto

class GithubRepoViewHolder(private val binding: GithubRepoViewItemBinding) :
  RecyclerView.ViewHolder(binding.root) {

  private var repo: GithubRepoDto? = null

  init {
    binding.root.setOnClickListener {
      repo?.url?.let { url ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        binding.root.context.startActivity(intent)
      }
    }
  }

  fun bind(githubRepo: GithubRepoDto?) {
    if (githubRepo == null) {
      val resources = itemView.resources
      with(binding) {
        repoName.text = resources.getString(R.string.loading)
        repoDescription.isGone = true
        repoLanguage.isGone = true
        repoStars.text = resources.getString(R.string.unknown)
        repoForks.text = resources.getString(R.string.unknown)
      }
    } else {
      githubRepo.showRepoData()
    }
  }

  private fun GithubRepoDto.showRepoData() {
    this@GithubRepoViewHolder.repo = this

    binding.repoName.text = fullName

    // if the description is missing, hide the TextView
    var descriptionVisibility = View.GONE
    if (description != null) {
      binding.repoDescription.text = description
      descriptionVisibility = View.VISIBLE
    }
    binding.repoDescription.visibility = descriptionVisibility

    binding.repoStars.text = stars.toString()
    binding.repoForks.text = forks.toString()

    // if the language is missing, hide the label and the value
    var languageVisibility = View.GONE
    if (!language.isNullOrEmpty()) {
      val resources = this@GithubRepoViewHolder.itemView.context.resources
      binding.repoLanguage.text = resources.getString(R.string.language, language)
      languageVisibility = View.VISIBLE
    }
    binding.repoLanguage.visibility = languageVisibility
  }

  companion object {
    fun create(parent: ViewGroup) = GithubRepoViewHolder(
      GithubRepoViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }
}