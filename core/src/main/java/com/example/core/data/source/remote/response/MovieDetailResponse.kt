package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    val id: Int = 0,
    @SerializedName("poster_path")
    val posterPath: String? = "",
    val title: String? = "No title",
    val popularity: Double? = 0.0,
    val overview: String? = "",
    val release_date: String? = "",
    val genres: List<GenresItemResponse>,
    @SerializedName("vote_count")
    val voteCount: Long? = 0,
)