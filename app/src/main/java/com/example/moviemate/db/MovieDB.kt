package com.example.moviemate.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Movie::class], version = 1)
@TypeConverters(Converter::class)
abstract class MovieDB: RoomDatabase() {
    companion object {
        const val NAME = "Movie-Database"
    }

    abstract fun getMovieDAO(): MovieDAO
}