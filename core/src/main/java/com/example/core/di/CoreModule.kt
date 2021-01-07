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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<AppDatabase>().movieDao() }
    factory { get<AppDatabase>().favoriteDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("beeflix".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "CapstoneProject.db")
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(ApiConfig.certificatePin)
            .build()
    }
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