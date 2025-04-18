package com.example.moviemate.widgets
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

class SearchMoviesViewModel : ViewModel() {
    private val _movieTitle = MutableStateFlow("")
    val movieTitle: StateFlow<String> = _movieTitle.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail: StateFlow<MovieDetail?> = _movieDetail.asStateFlow()

    fun setMovieTitle(title: String) {
        _movieTitle.value = title
    }

    fun fetchMovieDetail() {
        val title = _movieTitle.value
        if (title.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _movieDetail.value = null

            try {
                val url = URL("https://www.omdbapi.com/?t=${title}&apikey=779e8391")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val json = JSONObject(response)

                    if (json.getString("Response") == "True") {
                        _movieDetail.value = MovieDetail(
                            Title = json.getString("Title"),
                            Year = json.getString("Year"),
                            Rated = json.getString("Rated"),
                            Released = json.getString("Released"),
                            Runtime = json.getString("Runtime"),
                            Genre = json.getString("Genre"),
                            Director = json.getString("Director"),
                            Writer = json.getString("Writer"),
                            Actors = json.getString("Actors"),
                            Plot = json.getString("Plot")
                        )
                    }
                }
            }finally {
                _isLoading.value = false
            }
        }
    }
}