package com.app.kata.githubpagingcore.data.source.api

import com.app.kata.githubpagingcore.data.source.api.model.GithubReposSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubReposApiService {

  @GET("search/repositories?sort=stars")
  suspend fun searchRepos(
    @Query("q") query: String,
    @Query("page") page: Int,
    @Query("per_page") itemsPerPage: Int
  ): GithubReposSearchResponse

  companion object {
    private const val BASE_URL = "https://api.github.com/"

    fun create(): GithubReposApiService {
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
        .create(GithubReposApiService::class.java)
    }

  }
}

const val IN_QUALIFIER = "in:name,description"
