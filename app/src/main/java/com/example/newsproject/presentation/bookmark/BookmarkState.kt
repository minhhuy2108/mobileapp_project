package com.example.newsproject.presentation.bookmark

import com.example.newsproject.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)