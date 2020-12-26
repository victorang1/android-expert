package com.example.core.utils

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.network.ApiConfig.BASE_IMG_PATH
import com.example.core.data.source.remote.response.PopularMovieItemResponse
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
                it.overview,
                it.isFavorite
            )
        }

    fun mapDomainToEntities(it: Movie): MovieEntity =
        MovieEntity(
            it.id,
            it.image,
            it.title,
            it.popularity,
            it.voteCount,
            it.releaseDate,
            it.overview,
            it.isFavorite
        )
}