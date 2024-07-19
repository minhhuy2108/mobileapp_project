package com.example.newsproject.domain.usecases.app_entry

data class AppEntryUseCases(
    val readAppEntry: ReadAppEntry,    // Use case for reading app entry data, such as user preferences or first-run settings.
    val saveAppEntry: SaveAppEntry    // Use case for saving app entry data, such as user preferences or first-run settings.
)
