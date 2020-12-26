package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.source.local.room.AppDatabase
import com.example.core.data.source.local.room.FavoriteDao
import com.example.core.data.source.local.room.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "CapstoneProject.db")
            .fallbackToDestructiveMigration().build()

    @Provides
    fun provideFavoriteDao(db: AppDatabase): FavoriteDao = db.favoriteDao()

    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()
}