package com.example.newsproject.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsproject.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel // Annotate this class as a Hilt ViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases // Inject the use cases related to news
): ViewModel() {
    // Fetch news articles from specified sources and cache them in the ViewModel's scope
    val news = newsUseCases.getNews(
        sources = listOf("bbc-news","abc-news", "al-jazeera-english")
    ).cachedIn(viewModelScope) // Cache the results in the viewModelScope for efficient resource usage

}