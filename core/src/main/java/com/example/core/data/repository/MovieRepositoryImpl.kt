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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

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

            override fun shouldFetch(data: List<Movie>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<PopularMovieItemResponse>>> =
                remoteDataSource.getPopularMovies()

            override suspend fun saveCallResult(data: List<PopularMovieItemResponse>) {
                val movies = MappingUtil.mapResponsesToEntities(data)
                localDataSource.insertMovie(movies)
            }
        }.asFlow()

    override suspend fun searchMovie(name: String): Flow<Resource<List<Movie>>> {
        return flow {
            when (val apiResponse = remoteDataSource.searchMovie(name).first()) {
                is ApiResponse.Success -> {
                    val searchedMovies = MappingUtil.mapResponsesToEntities(apiResponse.data)
                    val domainEntities = MappingUtil.mapEntitiesToDomain(searchedMovies)
                    emit(Resource.Success<List<Movie>>(domainEntities))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success<List<Movie>>(mutableListOf()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error<List<Movie>>(apiResponse.errorMessage))
                }
            }
        }
    }
}