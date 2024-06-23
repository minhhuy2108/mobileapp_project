package com.example.newsproject.presentation.details

sealed class DetailsEvent {
    object SaveArticle : DetailsEvent()
}