package com.example.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Favorite
import com.example.core.domain.usecase.FavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class FavoriteViewModel(private val favoriteUseCase: FavoriteUseCase) : ViewModel() {

    val mChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val favorites = favoriteUseCase.getAllFavorite().asLiveData()

    val searchResult = mChannel.asFlow()
        .debounce(500)
        .distinctUntilChanged()
        .mapLatest { favoriteUseCase.searchFavorite(it) }
        .asLiveData()

    fun deleteFromFavorite(favorite: Favorite) = favoriteUseCase.removeFavorite(favorite)
}