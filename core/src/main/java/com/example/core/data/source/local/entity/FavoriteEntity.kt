package com.example.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val favoriteId: Int,
    val filmId: Int,
    val image: String,
    val title: String,
    val popularity: Double,
    val voteCount: Long,
    val releaseDate: String,
    val overview: String
)