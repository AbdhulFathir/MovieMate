package com.example.moviemate
import android.app.Application
import androidx.room.Room
import com.example.moviemate.db.MovieDB

class MainApplication: Application() {
    companion object {
        lateinit var database: MovieDB
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            MovieDB::class.java,
            MovieDB.NAME
        ).build()
    }
}