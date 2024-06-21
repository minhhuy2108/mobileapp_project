package com.example.newsproject.di

import android.app.Application
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
import com.example.newsproject.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

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
        newsApi: NewsApi
    ): NewsRepository = NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ): NewsUseCases{
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository)
        )
    }
}