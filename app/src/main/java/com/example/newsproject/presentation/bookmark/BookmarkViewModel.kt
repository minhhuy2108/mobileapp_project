package com.example.newsproject.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
@HiltViewModel // Annotates the class as a Hilt ViewModel
class BookmarkViewModel @Inject constructor(
    // Injects the NewsUseCases dependency into the ViewModel.
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    // Mutable state to hold the current state of bookmarked articles.
    private val _state = mutableStateOf(BookmarkState())
    // Publicly exposed state to observe changes in the UI.
    val state: State<BookmarkState> = _state

    // Initializes the ViewModel and triggers loading of bookmarked articles.
    init {
        getArticles()
    }

    // Retrieves bookmarked articles from the use cases and updates the state.
    private fun getArticles() {
        // Calls the use case to get a flow of articles.
        newsUseCases.getArticles() // Updates the state with the latest list of articles whenever the flow emits new data.
            .onEach {
            _state.value = _state.value.copy(articles = it)
        }  // Launches the flow collection in the ViewModel's coroutine scope.
     .launchIn(viewModelScope)
    }
}