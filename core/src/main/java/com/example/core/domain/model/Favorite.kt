package com.example.core.domain.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

data class Favorite(
    val id: Int,
    val filmId: Int,
    val image: String,
    val title: String,
    val popularity: Double,
    val voteCount: Long,
    val releaseDate: String,
    val overview: String
) : BaseObservable() {

    @Bindable
    fun getPopularityDisplay(): String {
        return popularity.toString()
    }

    @Bindable
    fun getVoteCountDisplay(): String {
        return voteCount.toString()
    }
}