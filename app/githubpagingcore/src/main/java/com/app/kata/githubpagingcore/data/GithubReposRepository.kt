package com.app.kata.githubpagingcore.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.kata.githubpagingcore.data.source.api.GithubReposApiService
import com.app.kata.githubpagingcore.data.source.api.GithubPagingSource
import com.app.kata.githubpagingcore.data.source.api.model.GithubRepoDto
import com.app.kata.githubpagingcore.data.source.local.GithubReposDatabase
import kotlinx.coroutines.flow.Flow

class GithubReposRepository(
  private val apiService: GithubReposApiService,
  private val database: GithubReposDatabase
) {

  fun getSearchResultStream(query: String): Flow<PagingData<GithubRepoDto>> {
    // appending '%' so we can allow other characters to be before and after the query string
    val dbQuery = "%${query.replace(' ', '%')}%"
    val pagingSourceFactory = { database.githubReposDao().reposByName(dbQuery) }


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
