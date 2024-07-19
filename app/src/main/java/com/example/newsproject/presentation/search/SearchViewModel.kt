package com.example.newsproject.presentation.search

import androidx.lifecycle.ViewModel
import com.example.newsproject.domain.usecases.news.NewsUseCases
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.paging.cachedIn
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel // Annotates the class as a Hilt ViewModel
class SearchViewModel @Inject constructor(
    // Injects the NewsUseCases dependency into the ViewModel.
    private val newsUseCases: NewsUseCases
): ViewModel() {
    // Mutable state for holding the current state of the search.
    private var _state = mutableStateOf(SearchState())
    // Publicly exposed state to observe changes in the UI.
    val state: State<SearchState> = _state


    fun onEvent(event: SearchEvent) {
        when (event) {
            // Updates the search query in the state when a new query is provided.
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = _state.value.copy(searchQuery = event.searchQuery)
            }
            // Initiates a search for news articles when a search event is triggered.
            is SearchEvent.SearchNews -> {
                searchNews()
            }
        }
    }

    // Performs the actual search for news articles based on the current search query.
    private fun searchNews() {
        // Retrieves news articles using the newsUseCases, filtered by the current search query and predefined sources.
        val articles = newsUseCases.searchNews(
            searchQuery = _state.value.searchQuery,
            sources = listOf("bbc-news", "abc-news", "al-jazeera-english")
        ).cachedIn(viewModelScope) // Caches the results in the ViewModel's scope to handle lifecycle changes.
        // Updates the state with the newly retrieved articles.
        _state.value = _state.value.copy(articles = articles)
    }
}