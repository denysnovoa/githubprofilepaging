package com.app.kata.githubpaging.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.app.kata.githubpaging.databinding.ActivitySearchRepositoriesBinding
import com.app.kata.githubpaging.di.Injection
import com.app.kata.githubpaging.ui.adapter.GithubProfileAdapter
import com.app.kata.githubpaging.ui.model.GithubProfileRepositoriesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class GithubProfilesRepositoriesActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySearchRepositoriesBinding
  private lateinit var viewModel: GithubProfileRepositoriesViewModel
  private val adapter = GithubProfileAdapter()
  private var searchJob: Job? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivitySearchRepositoriesBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
      .get(GithubProfileRepositoriesViewModel::class.java)

    binding.list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    binding.list.adapter = adapter

    val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
    search(query)
    initSearch(query)
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putString(LAST_SEARCH_QUERY, binding.searchRepo.text.trim().toString())
  }

  private fun updateRepoListFromInput() {
    binding.searchRepo.text.trim().let {
      if (it.isNotEmpty()) {
        binding.list.scrollToPosition(0)
        search(it.toString())
      }
    }
  }

  private fun search(query: String) {
    searchJob?.cancel()
    searchJob = lifecycleScope.launch {
      viewModel.searchGithubProfiles(query).collectLatest {
        adapter.submitData(it)
      }
    }
  }

  private fun initSearch(query: String) {
    initSearchRepoText(query)

    lifecycleScope.launch {
      adapter.loadStateFlow
        .distinctUntilChangedBy { it.refresh }
        .filter { it.refresh is LoadState.NotLoading }
        .collect { binding.list.scrollToPosition(0) }
    }
  }

  private fun initSearchRepoText(query: String) = with(binding.searchRepo) {
    setText(query)
    setOnEditorActionListener { _, actionId, _ ->
      if (actionId == EditorInfo.IME_ACTION_GO) {
        updateRepoListFromInput()
        true
      } else {
        false
      }
    }
    setOnKeyListener { _, keyCode, event ->
      if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
        updateRepoListFromInput()
        true
      } else {
        false
      }
    }
  }

  companion object {
    private const val LAST_SEARCH_QUERY: String = "last_search_query"
    private const val DEFAULT_QUERY = "Android"
  }
}
