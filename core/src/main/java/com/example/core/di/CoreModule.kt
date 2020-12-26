package com.example.core.di

import androidx.room.Room
import com.example.core.data.repository.FavoriteRepositoryImpl
import com.example.core.data.repository.MovieRepositoryImpl
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.room.AppDatabase
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiConfig
import com.example.core.data.source.remote.network.ApiService
import com.example.core.domain.repository.IFavoriteRepository
import com.example.core.domain.repository.IMovieRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<AppDatabase>().movieDao() }
    factory { get<AppDatabase>().favoriteDao() }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "CapstoneProject.db")
            .fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get()) }
    single<IMovieRepository> {
        MovieRepositoryImpl(get(), get())
    }
    single<IFavoriteRepository> {
        FavoriteRepositoryImpl(get())
    }
}