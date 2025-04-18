package com.example.moviemate.widgets

import com.example.moviemate.screens.SimpleMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class SearchByTitleViewModel : ViewModel() {
    private val _movieTitle = MutableStateFlow("")
    val movieTitle: StateFlow<String> = _movieTitle.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _results = MutableStateFlow<List<SimpleMovie>>(emptyList())
    val results: StateFlow<List<SimpleMovie>> = _results.asStateFlow()

    fun setMovieTitle(title: String) {
        _movieTitle.value = title
    }

    fun fetchMovies() {
        val title = _movieTitle.value
        if (title.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            try {
                val url = URL("https://www.omdbapi.com/?s=${title}&apikey=779e8391")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val json = JSONObject(response)
                    val movieList = mutableListOf<SimpleMovie>()

                    if (json.getString("Response") == "True") {
                        val searchArray = json.getJSONArray("Search")
                        for (i in 0 until searchArray.length()) {
                            val item = searchArray.getJSONObject(i)
                            movieList.add(
                                SimpleMovie(
                                    title = item.getString("Title"),
                                    year = item.getString("Year"),
                                    imdbID = item.getString("imdbID"),
                                    type = item.getString("Type"),
                                    poster = item.getString("Poster")
                                )
                            )
                        }
                    }
                    _results.value = movieList
                }
            } catch (e: Exception) {
                _results.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}