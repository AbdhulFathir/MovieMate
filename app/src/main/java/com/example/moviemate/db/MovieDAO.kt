package com.example.moviemate.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movies ORDER BY id DESC")
    fun getAllMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMovie(item: Movie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMovies(items: List<Movie>)

    @Query("Delete FROM movies where id = :id")
    fun deleteMovie(id: Int)

    // create your function to update with id and updated title
}