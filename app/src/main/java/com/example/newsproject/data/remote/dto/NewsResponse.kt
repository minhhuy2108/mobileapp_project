package com.example.newsproject.data.remote.dto

import com.example.newsproject.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)