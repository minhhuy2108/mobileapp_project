package com.example.newsproject.presentation.details

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.example.newsproject.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.newsproject.domain.model.Article
import kotlinx.coroutines.launch


@HiltViewModel // Annotates the class as a Hilt ViewModel
class DetailsViewModel @Inject constructor(
    // Injects the NewsUseCases dependency into the ViewModel.
    private val newsUseCases: NewsUseCases
): ViewModel(){
    // State variable to hold side effects such as messages about article deletion or saving.

    var sideEffect by mutableStateOf<String?>(null)
        private set // The setter is private to restrict modifications outside this class.
    fun onEvent(event: DetailsEvent){
        when(event){
            // Handles the event to either upsert (insert or update) or delete an article.
            is DetailsEvent.UpsertDeleteArticle ->{
                // Launches a coroutine within the ViewModel's scope to perform background operations.
                viewModelScope.launch {
                    // Retrieves the article from the database using its URL.

                    val article = newsUseCases.getArticle(url = event.article.url)
                    // If the article is not found, upserts (inserts or updates) it.

                    if (article == null){
                        upsertArticle(article = event.article)
                    }else{
                        // If the article is found, deletes it.
                        deleteArticle(article = event.article)
                    }

                }
            }
            // Clears the side effect message.
            is DetailsEvent.RemoveSideEffect ->{
                sideEffect = null
            }
        }
    }
    // Deletes an article and updates the side effect message to reflect the deletion.
    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article = article)
        sideEffect = "Article deleted"
    }
    // Upserts (inserts or updates) an article and updates the side effect message to reflect the saving.
    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticle(article = article)
        sideEffect = "Article Saved"
    }
}
