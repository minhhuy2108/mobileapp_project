package com.example.newsproject.presentation.details.components


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsproject.R
import com.example.newsproject.ui.theme.NewsProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    onBookMarkClick: () -> Unit,
    onBackClick: () -> Unit,
) {

    TopAppBar(
        title = {},
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = colorResource(id = R.color.body),
            navigationIconContentColor = colorResource(id = R.color.body),
        ),

        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = null,
                )
            }
        },
        actions = {

            IconButton(onClick = onBookMarkClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = null
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DetailsTopBarPreview() {
    NewsProjectTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background))
        DetailsTopBar(
            onBookMarkClick = { /*TODO*/ },
            ) {

        }
    }
}