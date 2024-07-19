package com.example.newsproject.presentation.search


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsproject.domain.model.Article
import com.example.newsproject.presentation.Dimens.MediumPadding1
import com.example.newsproject.presentation.common.ArticlesList
import com.example.newsproject.presentation.common.SearchBar



@Composable
fun SearchScreen(
    state: SearchState, // State holding the search query and article results
    event:(SearchEvent) -> Unit, // Function to handle search events
    navigateToDetails: (Article) -> Unit // Function to navigate to article details
) {

    Column(
        modifier = Modifier
            .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1)
            .statusBarsPadding()
    ) {
        // Display the search bar
        SearchBar(
            text = state.searchQuery,// Bind the search query to the text field
            readOnly = false, // Allow user to enter text
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = {
                event(SearchEvent.SearchNews)// Trigger search on search action
            }
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        // Display the list of articles if available
        state.articles?.let {
            val articles = it.collectAsLazyPagingItems() // Collect articles as lazy paging items
            ArticlesList(
                articles = articles, // Pass the articles to the list
                onClick = {
                    navigateToDetails(it) // Navigate to article details on click
                }
            )
        }
    }
}