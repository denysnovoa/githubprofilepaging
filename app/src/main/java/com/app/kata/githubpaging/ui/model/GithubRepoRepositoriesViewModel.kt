package com.app.kata.githubpaging.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.app.kata.githubpagingcore.data.GithubReposRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GithubRepoRepositoriesViewModel(private val repository: GithubReposRepository) :
  ViewModel() {

  private var currentQueryValue: String? = null
  private var currentSearchResult: Flow<PagingData<GithubRepoUiModel>>? = null

  fun searchGithubRepos(queryString: String): Flow<PagingData<GithubRepoUiModel>> {
    val lastResult = currentSearchResult
    if (queryString == currentQueryValue && lastResult != null) {
      return lastResult
    }
    currentQueryValue = queryString

    val newResult: Flow<PagingData<GithubRepoUiModel>> =
      repository.getSearchResultStream(queryString)
        .map { pagingData -> pagingData.map { GithubRepoUiModel.Item(it) } }
        .map {
          it.insertSeparators { before, after ->
            if (after == null) {
              // we're at the end of the list
              return@insertSeparators null
            }

            if (before == null) {
              // we're at the beginning of the list
              return@insertSeparators GithubRepoUiModel.SeparatorItem(
                "${after.roundedStarCount}0.000+ stars"
              )
            }
            // check between 2 items
            if (before.roundedStarCount > after.roundedStarCount) {
              if (after.roundedStarCount >= 1) {
                GithubRepoUiModel.SeparatorItem("${after.roundedStarCount}0.000+ stars")
              } else {
                GithubRepoUiModel.SeparatorItem("< 10.000+ stars")
              }
            } else {
              // no separator
              null
            }
          }
        }
        .cachedIn(viewModelScope)

    currentSearchResult = newResult

    return newResult
  }
}