package com.example.capstoneproject.di

import com.example.core.di.coreComponent
import org.koin.core.module.Module

val appComponent: List<Module> = listOf(
    useCaseModule, viewModelModule
) + coreComponent