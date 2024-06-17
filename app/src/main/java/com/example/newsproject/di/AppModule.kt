package com.example.newsproject.di

import android.app.Application
import com.example.newsproject.data.manger.LocalUserMangerImpl
import com.example.newsproject.domain.manger.LocalUserManger
import com.example.newsproject.domain.usecases.AppEntryUseCases
import com.example.newsproject.domain.usecases.ReadAppEntry
import com.example.newsproject.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}