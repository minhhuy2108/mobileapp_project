package com.example.newsproject.domain.repository

import com.example.newsproject.domain.model.Article
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData


interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>
}