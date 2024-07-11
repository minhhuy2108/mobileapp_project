package com.example.newsproject.di

import android.app.Application
import com.example.newsproject.data.local.NewsTypeConverter
import com.example.newsproject.data.manger.LocalUserMangerImpl
import com.example.newsproject.data.remote.NewsApi
import com.example.newsproject.data.repository.NewsRepositoryImpl
import com.example.newsproject.domain.manger.LocalUserManger
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
    @Provides
    @Singleton
    fun provideLocalUserManger(
        application: Application
    ): LocalUserManger = LocalUserMangerImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManger
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger)
    )
    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository = NewsRepositoryImpl(newsApi,newsDao)

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

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = "news_db"
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao

}