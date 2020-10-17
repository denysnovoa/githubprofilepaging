package com.app.kata.githubpaggingcore.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.kata.githubpaggingcore.data.source.api.GithubPagingApiService
import com.app.kata.githubpaggingcore.data.source.api.GithubPagingSource
import com.app.kata.githubpaggingcore.data.source.api.model.GithubProfileDto
import kotlinx.coroutines.flow.Flow

class GithubProfileRepository(private val apiService: GithubPagingApiService) {

  fun getSearchResultStream(query: String): Flow<PagingData<GithubProfileDto>> {
    return Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { GithubPagingSource(apiService, query) }
    ).flow
  }
}

private const val NETWORK_PAGE_SIZE = 50
