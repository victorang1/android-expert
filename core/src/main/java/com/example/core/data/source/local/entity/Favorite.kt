package com.example.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "favorite")
data class Favorite(

    @NonNull
    @ColumnInfo(name = "favoriteId")
    val favoriteId: String,

    val image: String,

    val title: String,

    val category: String,

    val releaseDate: String,

    val overview: String
)