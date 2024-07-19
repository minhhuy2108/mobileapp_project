package com.example.newsproject.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsproject.data.remote.dto.NewsResponse
import com.example.newsproject.domain.model.Article

// Defines a PagingSource that loads pages of Article data from the NewsApi.
class NewsPagingSource(
    private val newsApi: NewsApi, // The API service to fetch news data.
    private val sources: String // The news sources to fetch articles from.
) : PagingSource<Int, Article>(){
    // Keeps track of the total number of news articles loaded so far.
    private var totalNewsCount = 0

    // Loads a page of data.
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        // Determines the current page to load. Defaults to page 1 if not specified.
        val page = params.key ?: 1
        return try {
            // Makes a network request to fetch news articles for the specified page and sources.
            val newsResponse = newsApi.getNews(sources = sources, page = page)
            // Updates the total count of news articles loaded so far.
            totalNewsCount += newsResponse.articles.size
            // Removes duplicate articles based on their title.
            val articles = newsResponse.articles.distinctBy{it.title}
            // Returns the loaded page of articles, along with keys for the next and previous pages.
            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace() // Logs the exception stack trace for debugging.
            // Returns an error result if the network request fails.
            LoadResult.Error(
                throwable = e
            )
        }
    }

    // Provides the key for the page to refresh based on the current state of the PagingSource.
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        // Determines the anchor position, which is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            // Finds the closest page to the anchor position.
            val anchorPage = state.closestPageToPosition(anchorPosition)
            // Calculates the key for the next or previous page.
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}