package com.app.kata.githubpaggingcore.repository.source.api

import com.app.kata.githubpaggingcore.repository.source.api.model.GithubProfileSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubPagingApiService {

    @GET("search/repositories?sort=stars")
    fun searchRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): GithubProfileSearchResponse {
        TODO("Not yet implemented")
    }

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GithubPagingApiService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubPagingApiService::class.java)
        }

    }
}

const val IN_QUALIFIER = "in:name,description"
