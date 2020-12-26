package com.example.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(

    @PrimaryKey
    val favoriteId: String,

    val image: String,

    val title: String,

    val category: String,

    val releaseDate: String,

    val overview: String
)