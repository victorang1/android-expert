package com.example.core.data.repository

import com.example.core.data.source.local.LocalDataSource
import com.example.core.domain.model.Favorite
import com.example.core.domain.model.Movie
import com.example.core.domain.repository.IFavoriteRepository
import com.example.core.utils.MappingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoriteRepositoryImpl(private val localDataSource: LocalDataSource) : IFavoriteRepository {

    override fun insertToFavorite(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            val favorite = MappingUtil.mapDomainToFavoriteEntities(movie)
            localDataSource.insertFavorite(favorite)
        }
    }

    override fun removeFromFavorite(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.removeFavorite(movie.id)
        }
    }

    override fun isFavorite(movieId: Int): Flow<Boolean> {
        return localDataSource.isFavorite(movieId).map {
            !it.isNullOrEmpty()
        }
    }

    override fun getFavoriteData(): Flow<List<Favorite>> =
        localDataSource.getAllFavorites().map {
            MappingUtil.mapEntitiesToFavoriteDomain(it)
        }
}