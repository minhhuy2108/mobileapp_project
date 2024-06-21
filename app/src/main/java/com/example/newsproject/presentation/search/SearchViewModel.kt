package com.example.newsproject.presentation.search

import androidx.lifecycle.ViewModel
import com.example.newsproject.domain.usecases.news.NewsUseCases
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.paging.cachedIn
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {
    private var _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = _state.value.copy(searchQuery = event.searchQuery)
            }

            is SearchEvent.SearchNews -> {
                searchNews()
            }
        }
    }

    private fun searchNews() {
        val articles = newsUseCases.searchNews(
            searchQuery = _state.value.searchQuery,
            sources = listOf("bbc-news", "abc-news", "al-jazeera-english")
        ).cachedIn(viewModelScope)
        _state.value = _state.value.copy(articles = articles)
    }
}