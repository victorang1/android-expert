package com.example.core.domain.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val image: String,
    val title: String,
    val popularity: Double,
    val voteCount: Long,
    val releaseDate: String,
    val overview: String
) : BaseObservable(), Parcelable {

    @Bindable
    fun getPopularityDisplay(): String {
        return popularity.toString()
    }

    @Bindable
    fun getVoteCountDisplay(): String {
        return voteCount.toString()
    }
}