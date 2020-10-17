package com.app.kata.githubpaggingcore.repository.source.api

import com.app.kata.githubpaggingcore.repository.source.api.model.GithubProfileSearchResponse

class GithubPagingApiService {
    fun searchRepos(apiQuery: String, position: Int, loadSize: Int): GithubProfileSearchResponse {
        TODO("Not yet implemented")
    }

}

const val IN_QUALIFIER = "in:name,description"
