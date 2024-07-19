package com.example.newsproject.domain.usecases.news

// Use cases encapsulate the business logic for interacting with the news data.
data class NewsUseCases(
    val getNews: GetNews,    // Use case for retrieving news articles from a list of sources.
    val searchNews: SearchNews,    // Use case for searching news articles based on a search query and sources.
    val upsertArticle: UpsertArticle,    // Use case for inserting or updating a news article in the local database.
    val deleteArticle: DeleteArticle,    // Use case for deleting a news article from the local database.
    val getArticles: GetArticles,    // Use case for retrieving a list of all news articles from the local database.
    val getArticle: GetArticle    // Use case for retrieving a single news article by its URL from the local database.

)
