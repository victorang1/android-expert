package com.example.core.utils

import android.view.View
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
fun View.onClick(): Flow<Unit> = callbackFlow {
    this@onClick.setOnClickListener {
        this.offer(Unit)
    }
    awaitClose { this@onClick.setOnClickListener(null) }
}