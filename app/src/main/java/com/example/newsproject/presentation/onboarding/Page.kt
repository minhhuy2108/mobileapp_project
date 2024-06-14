package com.example.newsproject.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.newsproject.R


data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "title",
        description = "description",
        image = R.drawable.onboarding1
    ),Page(
        title = "title",
        description = "description",
        image = R.drawable.onboarding2
    ),Page(
        title = "title",
        description = "description",
        image = R.drawable.onboarding3
    )
)