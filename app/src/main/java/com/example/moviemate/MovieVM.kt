package com.example.moviemate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemate.db.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieVM: ViewModel() {
    private val movieDAO = MainApplication.database.getMovieDAO()
    val movies: LiveData<List<Movie>> = movieDAO.getAllMovies()

    fun addTodoItem(title: String,
                    year: String,
                    rated: String,
                    released: String,
                    runtime: String,
                    genre: String,
                    director: String,
                    writer: String,
                    actors: String,
                    plot: String,) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = Movie(
                title = title,
                year = year,
                rated = rated,
                released = released,
                runtime = runtime,
                genre = genre,
                director = director,
                writer = writer,
                actors = actors,
                plot = plot
            )
            movieDAO.addMovie(item)
        }
    }

    fun addTodoItems(movies: List<Movie>) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDAO.addMovies(movies)
        }
    }

    fun deleteTodoItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDAO.deleteMovie(id)
        }
    }

    fun searchByActor(query: String): LiveData<List<Movie>> {
        return movieDAO.searchByActor(query)
    }


}