package com.app.kata.githubpagingcore.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.app.kata.githubpagingcore.data.source.api.GithubPagingApiService
import com.app.kata.githubpagingcore.data.source.api.model.GithubProfileDto
import com.app.kata.githubpagingcore.data.source.local.GithubProfileDatabase

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
    TODO("Not yet implemented")
  }
}