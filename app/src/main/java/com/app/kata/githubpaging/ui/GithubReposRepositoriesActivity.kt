package com.app.kata.githubpaging.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.app.kata.githubpaging.databinding.ActivitySearchRepositoriesBinding
import com.app.kata.githubpaging.di.Injection
import com.app.kata.githubpaging.ui.adapter.GithubReposAdapter
import com.app.kata.githubpaging.ui.adapter.GithubRepoLoadStateAdapter
import com.app.kata.githubpaging.ui.model.GithubRepoRepositoriesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class GithubReposRepositoriesActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySearchRepositoriesBinding
  private lateinit var viewModel: GithubRepoRepositoriesViewModel
  private val adapter = GithubReposAdapter()
  private var searchJob: Job? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivitySearchRepositoriesBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
      .get(GithubRepoRepositoriesViewModel::class.java)

    binding.list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    initAdapter()

    val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
    search(query)
    initSearch(query)

    binding.retryButton.setOnClickListener { adapter.retry() }
  }

  private fun initAdapter() {
    binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
      header = GithubRepoLoadStateAdapter { adapter.retry() },
      footer = GithubRepoLoadStateAdapter { adapter.retry() }
    )

    adapter.addLoadStateListener { loadState ->
      binding.list.isVisible = loadState.source.refresh is LoadState.NotLoading
      binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
      binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

      // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
      val errorState = loadState.source.append as? LoadState.Error
        ?: loadState.source.prepend as? LoadState.Error
        ?: loadState.append as? LoadState.Error
        ?: loadState.prepend as? LoadState.Error
      errorState?.let {
        Toast.makeText(
          this,
          "\uD83D\uDE28 Wooops ${it.error}",
          Toast.LENGTH_LONG
        ).show()
      }
    }
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
      viewModel.searchGithubRepos(query).collectLatest {
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
