package com.example.core.di

import com.example.core.data.repository.FavoriteRepositoryImpl
import com.example.core.data.repository.MovieRepositoryImpl
import com.example.core.domain.repository.IFavoriteRepository
import com.example.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepository: MovieRepositoryImpl): IMovieRepository

    @Binds
    abstract fun provideFavoriteRepository(favoriteRepository: FavoriteRepositoryImpl): IFavoriteRepository
}