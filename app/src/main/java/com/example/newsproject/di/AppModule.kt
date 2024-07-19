package com.example.newsproject.di

import android.app.Application
import com.example.newsproject.data.local.NewsTypeConverter
import com.example.newsproject.data.manger.LocalUserMangerImpl
import com.example.newsproject.data.remote.NewsApi
import com.example.newsproject.data.repository.NewsRepositoryImpl
import com.example.newsproject.domain.manager.LocalUserManger
import com.example.newsproject.domain.repository.NewsRepository
import com.example.newsproject.domain.usecases.app_entry.AppEntryUseCases
import com.example.newsproject.domain.usecases.app_entry.ReadAppEntry
import com.example.newsproject.domain.usecases.app_entry.SaveAppEntry
import com.example.newsproject.domain.usecases.news.GetNews
import com.example.newsproject.domain.usecases.news.NewsUseCases
import com.example.newsproject.domain.usecases.news.SearchNews
import com.example.newsproject.domain.usecases.news.GetArticles
import com.example.newsproject.domain.usecases.news.DeleteArticle
import com.example.newsproject.domain.usecases.news.UpsertArticle
import com.example.newsproject.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.newsproject.data.local.NewsDatabase
import com.example.newsproject.data.local.NewsDao
import androidx.room.Room
import com.example.newsproject.domain.usecases.news.GetArticle

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides an instance of LocalUserManger using Dagger Hilt.
    @Provides
    @Singleton
    fun provideLocalUserManger(
        application: Application
    ): LocalUserManger = LocalUserMangerImpl(context = application)

    // Provides an instance of AppEntryUseCases using Dagger Hilt.
    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManger
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger)
    )

    // Provides an instance of NewsApi using Retrofit.
    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)// Base URL for the API.
            .addConverterFactory(GsonConverterFactory.create())// Converts JSON responses to Kotlin objects.
            .build()
            .create(NewsApi::class.java)// Creates an instance of the NewsApi interface.
    }

    // Provides an instance of NewsRepository using NewsApi and NewsDao.
    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository = NewsRepositoryImpl(newsApi,newsDao)

    // Provides an instance of NewsUseCases using NewsRepository and NewsDao.
    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsDao: NewsDao
    ): NewsUseCases{
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            getArticles = GetArticles(newsRepository),
            getArticle = GetArticle(newsRepository)
        )
    }

    // Provides an instance of NewsDatabase using Room.
    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = "news_db" // Name of the database.
        ).addTypeConverter(NewsTypeConverter()) // Adds type converter for custom types.
            .fallbackToDestructiveMigration() // Handles schema changes by recreating the database.
            .build()
    }

    // Provides an instance of NewsDao from NewsDatabase.
    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao

}