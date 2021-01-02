package com.example.core.utils

import com.example.core.data.source.local.entity.FavoriteEntity
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.network.ApiConfig.BASE_IMG_PATH
import com.example.core.data.source.remote.response.PopularMovieItemResponse
import com.example.core.domain.model.Favorite
import com.example.core.domain.model.Movie

object MappingUtil {

    fun mapResponsesToEntities(input: List<PopularMovieItemResponse>): List<MovieEntity> {
        val tourismList = mutableListOf<MovieEntity>()
        input.map {
            val tourism = MovieEntity(
                it.id,
                BASE_IMG_PATH + it.posterPath,
                it.title,
                it.popularity,
                it.voteCount,
                it.releaseDate,
                it.overview
            )
            tourismList.add(tourism)
        }
        return tourismList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                it.id,
                it.image,
                it.title,
                it.popularity,
                it.voteCount,
                it.releaseDate,
                it.overview
            )
        }

    fun mapEntitiesToFavoriteDomain(input: List<FavoriteEntity>): List<Favorite> =
        input.map {
            Favorite(
                it.favoriteId,
                it.filmId,
                it.image,
                it.title,
                it.popularity,
                it.voteCount,
                it.releaseDate,
                it.overview
            )
        }

    fun mapFavoriteToMovie(favorite: Favorite): Movie =
        Movie(
            favorite.filmId,
            favorite.image,
            favorite.title,
            favorite.popularity,
            favorite.voteCount,
            favorite.releaseDate,
            favorite.overview
        )

    fun mapDomainToFavoriteEntities(it: Movie): FavoriteEntity =
        FavoriteEntity(
            0,
            it.id,
            it.image,
            it.title,
            it.popularity,
            it.voteCount,
            it.releaseDate,
            it.overview
        )
}