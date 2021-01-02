package com.example.capstoneproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest

@FlowPreview
@ExperimentalCoroutinesApi
class HomeViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val mChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val movies = movieUseCase.getAllMovie().asLiveData()

    val searchResult = mChannel.asFlow()
        .debounce(500)
        .distinctUntilChanged()
        .mapLatest { query ->
            if(query.isEmpty()) {
                movieUseCase.getAllMovie()
            }
            else movieUseCase.searchMovie(query)
        }
        .asLiveData()
}