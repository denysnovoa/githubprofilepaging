package com.app.kata.githubpaggingcore.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.kata.githubpaggingcore.data.GithubProfileRepository
import com.app.kata.githubpaggingcore.data.source.api.model.GithubProfileDto
import kotlinx.coroutines.flow.Flow

class GithubProfileRepositoriesViewModel(private val repository: GithubProfileRepository) :
    ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<GithubProfileDto>>? = null

    fun searchGithubProfiles(queryString: String): Flow<PagingData<GithubProfileDto>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString

        val newResult = repository.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult

        return newResult
    }
}
