package com.houston.filmographer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [MovieEntity::class])
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}