package com.example.newsproject.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsproject.data.local.NewsDao
import com.example.newsproject.data.remote.NewsPagingSource
import com.example.newsproject.data.remote.NewsApi
import com.example.newsproject.data.remote.SearchNewsPagingSource
//import com.example.newsproject.data.remote.SearchNewsPagingSource
import com.example.newsproject.domain.model.Article
import com.example.newsproject.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

// Implementation of the NewsRepository interface, providing methods to interact with news data.
class NewsRepositoryImpl(
    // The API service used to fetch news articles.
    private val newsApi: NewsApi,
    // The local database DAO for news articles.
    private val newsDao: NewsDao
): NewsRepository {
    // Fetches news articles from the API and returns them as a Flow of PagingData.
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),// Configures paging with a page size of 10.
            pagingSourceFactory = {
                // Creates a new instance of NewsPagingSource for fetching news articles.
                NewsPagingSource(newsApi = newsApi, sources = sources.joinToString(separator = ","))
            }
        ).flow // Returns the Flow of PagingData.
    }

    // Fetches search results for news articles from the API and returns them as a Flow of PagingData.
    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10), // Configures paging with a page size of 10.
            pagingSourceFactory = {
                // Creates a new instance of SearchNewsPagingSource for searching news articles.
                SearchNewsPagingSource(
                    searchQuery = searchQuery,
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow // Returns the Flow of PagingData.
    }

    // Inserts or updates an article in the local database.
    override suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article) // Calls the DAO method to upsert the article.
    }

    // Deletes an article from the local database.
    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article) // Calls the DAO method to delete the article.
    }

    // Retrieves a list of articles from the local database.
    override fun getArticles(): Flow<List<Article>> {
        return newsDao.getArticles() // Returns a Flow of a list of articles from the DAO.
    }

    // Retrieves a single article from the local database by URL.
    override suspend fun getArticle(url: String): Article? {
        return newsDao.getArticle(url = url) // Calls the DAO method to get an article by URL.
    }
}