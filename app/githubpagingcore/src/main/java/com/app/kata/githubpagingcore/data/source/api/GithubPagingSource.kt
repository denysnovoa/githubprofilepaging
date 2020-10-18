package com.app.kata.githubpagingcore.data.source.api

import androidx.paging.PagingSource
import com.app.kata.githubpagingcore.data.source.api.model.GithubRepoDto
import retrofit2.HttpException
import java.io.IOException

class GithubPagingSource(
  private val apiService: GithubReposApiService,
  private val query: String
) : PagingSource<Int, GithubRepoDto>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepoDto> {
    val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
    val apiQuery = query + IN_QUALIFIER

    return try {
      val profilesDto = apiService.searchRepos(apiQuery, position, params.loadSize).items

      LoadResult.Page(
        data = profilesDto,
        prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
        nextKey = if (profilesDto.isEmpty()) null else position + 1
      )

    } catch (exception: IOException) {
      return LoadResult.Error(exception)
    } catch (exception: HttpException) {
      return LoadResult.Error(exception)
    }

  }

  companion object {
    const val GITHUB_STARTING_PAGE_INDEX = 1
  }
}
