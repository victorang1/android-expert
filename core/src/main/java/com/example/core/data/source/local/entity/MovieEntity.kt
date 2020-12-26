package com.example.core.data.source.local.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val image: String,
    val title: String,
    val popularity: Double,
    val voteCount: Long,
    val releaseDate: String,
    val overview: String,
)