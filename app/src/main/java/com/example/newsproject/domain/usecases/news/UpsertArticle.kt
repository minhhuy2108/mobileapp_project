package com.example.newsproject.domain.usecases.news

import com.example.newsproject.data.local.NewsDao
import com.example.newsproject.domain.model.Article
import com.example.newsproject.domain.repository.NewsRepository

class UpsertArticle(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article){
        newsRepository.upsertArticle(article = article)
    }

}