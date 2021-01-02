package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PopularMovieItemResponse(
    @field:SerializedName("id")
    val id: Int = 0,
    @field:SerializedName("poster_path")
    val posterPath: String = "",
    @field:SerializedName("title")
    val title: String = "No title",
    val popularity: Double = 0.0,
    @field:SerializedName("release_date")
    val releaseDate: String = "",
    @field:SerializedName("vote_count")
    val voteCount: Long = 0,
    @field:SerializedName("overview")
    val overview: String = ""
)