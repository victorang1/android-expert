package com.example.capstoneproject.di

import com.example.capstoneproject.ui.detail.DetailViewModel
import com.example.capstoneproject.ui.home.HomeViewModel
import com.example.core.domain.interactor.MovieInteractor
import com.example.core.domain.usecase.FavoriteUseCase
import com.example.core.domain.usecase.MovieUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}