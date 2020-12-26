package com.example.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.source.local.entity.Favorite
import com.example.core.data.source.local.entity.Movie

@Database(entities = [Movie::class, Favorite::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun favoriteDao(): FavoriteDao
}