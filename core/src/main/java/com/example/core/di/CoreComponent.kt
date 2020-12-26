package com.example.core.di

import org.koin.core.module.Module

val coreComponent: List<Module> = listOf(
    databaseModule,
    networkModule,
    repositoryModule
)