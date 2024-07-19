package com.example.newsproject.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsproject.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    // Inserts an article into the database. If the article already exists (based on primary key),
    // it replaces the existing entry.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    // Deletes a specific article from the database.
    @Delete
    suspend fun delete(article: Article)

    // Retrieves all articles from the database as a Flow, allowing for observation of changes in real-time.
    @Query("SELECT * FROM Article")
    fun getArticles(): Flow<List<Article>>

    // Retrieves a specific article from the database based on its URL. Returns null if no article is found.
    @Query("SELECT * FROM Article WHERE url=:url")
    suspend fun getArticle(url: String): Article?

}