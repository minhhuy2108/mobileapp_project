package com.example.newsproject.presentation.search

import androidx.paging.PagingData
import com.example.newsproject.domain.model.Article
import kotlinx.coroutines.flow.Flow


data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
) {

}