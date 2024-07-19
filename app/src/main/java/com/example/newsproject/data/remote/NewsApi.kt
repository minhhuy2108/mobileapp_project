package com.example.newsproject.data.remote

import com.example.newsproject.data.remote.dto.NewsResponse
import com.example.newsproject.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    // Retrieves news articles from the API. This method corresponds to the "everything" endpoint.

    @GET("everything")
    suspend fun getNews(
        // Specifies the page number for pagination.
        @Query("page") page: Int,
        // Specifies the sources from which to fetch news articles.
        @Query("sources") sources: String,
        // The API key for authentication. Defaults to a constant API_KEY if not provided.
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    // Searches for news articles from the API based on a query string. This method also corresponds to the "everything" endpoint.
    @GET("everything")
    suspend fun searchNews(
        // The search query string to filter news articles.
        @Query("q") searchQuery: String,
        // Specifies the sources from which to fetch news articles.
        @Query("sources") sources: String,
        // Specifies the page number for pagination.
        @Query("page") page: Int,
        // The API key for authentication. Defaults to a constant API_KEY if not provided.
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse
}

