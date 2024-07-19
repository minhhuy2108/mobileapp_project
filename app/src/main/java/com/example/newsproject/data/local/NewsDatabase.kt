package com.example.newsproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsproject.domain.model.Article

// Specifies that this class is a Room Database with the Article entity and version 2 of the schema.
@Database(entities = [Article::class],version = 2,)
// Indicates that this database uses type converters, which handle custom data types that are not natively supported by Room.
@TypeConverters(NewsTypeConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    // Abstract property to access the DAO for performing database operations.
    abstract val newsDao: NewsDao

}