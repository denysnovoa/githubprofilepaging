package com.app.kata.githubpagging.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.kata.githubpagging.ui.adapter.GithubProfileAdapter
import com.app.kata.githubpagging.ui.model.GithubProfileRepositoriesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GithubProfilesRepositoriesActivity : AppCompatActivity() {

  private lateinit var viewModel: GithubProfileRepositoriesViewModel
  private val adapter = GithubProfileAdapter()
  private var searchJob: Job? = null

  private fun search(query: String) {
    searchJob?.cancel()
    searchJob = lifecycleScope.launch {
      viewModel.searchGithubProfiles(query).collectLatest {
        adapter.submitData(it)
      }
    }
  }
}
