package com.example.core.data.repository

import com.example.core.data.NetworkBoundResource
import com.example.core.data.Resource
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.PopularMovieItemResponse
import com.example.core.domain.model.Movie
import com.example.core.domain.repository.IMovieRepository
import com.example.core.utils.MappingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {

    override fun getMovieData(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<PopularMovieItemResponse>>() {

            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getAllMovies().map {
                    MappingUtil.mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?): Boolean = data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<PopularMovieItemResponse>>> =
                remoteDataSource.getPopularMovies()

            override suspend fun saveCallResult(data: List<PopularMovieItemResponse>) {
                val movies = MappingUtil.mapResponsesToEntities(data)
                localDataSource.insertMovie(movies)
            }
        }.asFlow()

    override fun setFavorite(movie: Movie, status: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val entity = MappingUtil.mapDomainToEntities(movie)
            localDataSource.updateMovie(entity, status)
        }
    }
}