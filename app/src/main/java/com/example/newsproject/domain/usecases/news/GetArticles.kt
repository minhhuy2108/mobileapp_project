package com.example.newsproject.domain.usecases.news

import com.example.newsproject.data.local.NewsDao
import com.example.newsproject.domain.model.Article
import com.example.newsproject.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetArticles(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(): Flow<List<Article>>{
        return newsRepository.getArticles()
    }

}