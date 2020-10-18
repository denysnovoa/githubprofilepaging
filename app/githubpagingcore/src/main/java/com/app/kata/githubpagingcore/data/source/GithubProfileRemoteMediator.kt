package com.app.kata.githubpagingcore.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.app.kata.githubpagingcore.data.source.api.GithubPagingApiService
import com.app.kata.githubpagingcore.data.source.api.GithubPagingSource.Companion.GITHUB_STARTING_PAGE_INDEX
import com.app.kata.githubpagingcore.data.source.api.IN_QUALIFIER
import com.app.kata.githubpagingcore.data.source.api.model.GithubProfileDto
import com.app.kata.githubpagingcore.data.source.local.GithubProfileDatabase
import com.app.kata.githubpagingcore.data.source.local.GithubProfileRemoteKeysDto
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class GithubProfileRemoteMediator(
  private val query: String,
  private val service: GithubPagingApiService,
  private val database: GithubProfileDatabase
) : RemoteMediator<Int, GithubProfileDto>() {

  override suspend fun load(
    loadType: LoadType,
    state: PagingState<Int, GithubProfileDto>
  ): MediatorResult {
    val page: Int = when (loadType) {
      LoadType.REFRESH -> {
        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
        remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
      }
      LoadType.PREPEND -> {
        val remoteKeys = getRemoteKeyForFirstItem(state)
          ?:
          // The LoadType is PREPEND so some data was loaded before,
          // so we should have been able to get remote keys
          // If the remoteKeys are null, then we're an invalid state and we have a bug
          throw InvalidObjectException("Remote key and the prevKey should not be null")
        // If the previous key is null, then we can't request more data
        remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)

        remoteKeys.prevKey
      }
      LoadType.APPEND -> {
        val remoteKeys = getRemoteKeyForLastItem(state)
        if (remoteKeys?.nextKey == null) {
          throw InvalidObjectException("Remote key should not be null for $loadType")
        }
        remoteKeys.nextKey
      }
    }
    val apiQuery = query + IN_QUALIFIER

    try {
      val apiResponse = service.searchRepos(apiQuery, page, state.config.pageSize)

      val repos = apiResponse.items
      val endOfPaginationReached = repos.isEmpty()

      database.withTransaction {
        if (loadType == LoadType.REFRESH) {
          database.remoteKeysDao().clearRemoteKeys()
          database.githubProfileDao().clearProfiles()
        }
        val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
        val nextKey = if (endOfPaginationReached) null else page + 1
        val keys = repos.map {
          GithubProfileRemoteKeysDto(githubProfileId = it.id, prevKey = prevKey, nextKey = nextKey)
        }

        database.remoteKeysDao().insertAll(keys)
        database.githubProfileDao().insertAll(repos)
      }

      return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
    } catch (exception: IOException) {
      return MediatorResult.Error(exception)
    } catch (exception: HttpException) {
      return MediatorResult.Error(exception)
    }
  }

  private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GithubProfileDto>):
      GithubProfileRemoteKeysDto? {
    // Get the last page that was retrieved, that contained items.
    // From that last page, get the last item
    return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
      // Get the remote keys of the last item retrieved
      database.remoteKeysDao().remoteKeysRepoId(repo.id)
    }
  }

  private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GithubProfileDto>):
      GithubProfileRemoteKeysDto? {
    // Get the first page that was retrieved, that contained items.
    // From that first page, get the first item
    return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { repo ->
      // Get the remote keys of the first items retrieved
      database.remoteKeysDao().remoteKeysRepoId(repo.id)
    }
  }

  private suspend fun getRemoteKeyClosestToCurrentPosition(
    state: PagingState<Int, GithubProfileDto>
  ): GithubProfileRemoteKeysDto? {
    // The paging library is trying to load data after the anchor position
    // Get the item closest to the anchor position
    return state.anchorPosition?.let { position ->
      state.closestItemToPosition(position)?.id?.let { repoId ->
        database.remoteKeysDao().remoteKeysRepoId(repoId)
      }
    }
  }
}
