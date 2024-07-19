package com.example.newsproject.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsproject.domain.model.Article
import java.lang.Exception

class SearchNewsPagingSource(
    // The API service used to fetch news articles.
    private val newsApi: NewsApi,
    // The search query used to filter news articles.
    private val searchQuery: String,
    // The sources from which to fetch news articles.
    private val sources: String
) : PagingSource<Int, Article>() {
    // Determines the key for refreshing the list, based on the current state.
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
    // Keeps track of the total number of news articles fetched.
    private var totalNewsCount = 0

    // Loads a page of news articles.
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        // Determines the page number to load, defaulting to 1 if not specified.
        val page = params.key ?: 1
        return try {
            // Fetches news articles based on the search query and sources from the API.
            val newsResponse = newsApi.searchNews(searchQuery = searchQuery,sources = sources, page = page)
            // Updates the total count of news articles fetched.
            totalNewsCount += newsResponse.articles.size
            // Ensures articles are distinct by their title.
            val articles = newsResponse.articles.distinctBy{it.title}
            // Returns a successful LoadResult containing the articles and pagination info.
            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            // Logs the exception and returns an error LoadResult.
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }

}