package com.example.newsproject.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.newsproject.R
import com.example.newsproject.domain.model.Article
import com.example.newsproject.presentation.Dimens.MediumPadding1
import com.example.newsproject.presentation.common.ArticlesList
import com.example.newsproject.presentation.navgraph.Route

@Composable
fun BookmarkScreen(
    state: BookmarkState, // State holding the list of bookmarked articles
    navigateToDetails: (Article) -> Unit // Function to navigate to article details
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1)
    ) {
        // Display the title "Bookmark"
        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(
                id = R.color.text_title
            )
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        // Display the list of bookmarked articles
        ArticlesList(
            articles = state.articles,
            onClick = { navigateToDetails(it) } // Handle click event to navigate to details
        )
    }
}