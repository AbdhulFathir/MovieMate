package com.example.moviemate.screens

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviemate.CustomButton
import com.example.moviemate.R
import com.example.moviemate.ui.theme.MovieMateTheme
import com.example.moviemate.widgets.MovieDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class SearchMovies : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieMateTheme  {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var movieTitle by remember { mutableStateOf("") }
                    var movieDetail by remember { mutableStateOf<MovieDetail?>(null) }
                    var isLoading by remember { mutableStateOf(false) }

                    Image(
                        painter = painterResource(id = R.drawable.bg_image),
                        contentDescription = "Background Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                setResult(Activity.RESULT_OK)
                                finish()
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                            Text(
                                text = "Search Movies",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }


                        Text("Search Movie from OMDb API", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = movieTitle,
                            onValueChange = { movieTitle = it },
                            label = { Text("Enter movie title") }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        CustomButton(text = "Retrieve Movie") {
                            fetchMovieDetail(movieTitle, onResult = {
                                movieDetail = it
                            }, setLoading = {
                                isLoading = it
                            })
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        CustomButton(text = "Save Movie to DB") {
                            // TODO: Save to Room
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                        }
                        movieDetail?.let { movie ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFBBDEFB) // Light Blue
                                ),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(text = movie.Title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "Year: ${movie.Year}")
                                    Text(text = "Rated: ${movie.Rated}")
                                    Text(text = "Released: ${movie.Released}")
                                    Text(text = "Runtime: ${movie.Runtime}")
                                    Text(text = "Genre: ${movie.Genre}")
                                    Text(text = "Director: ${movie.Director}")
                                    Text(text = "Writer: ${movie.Writer}")
                                    Text(text = "Actors: ${movie.Actors}")
                                    Text(text = "Plot: ${movie.Plot}", maxLines = 3)
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

fun fetchMovieDetail(
    title: String,
    onResult: (MovieDetail?) -> Unit,
    setLoading: (Boolean) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        setLoading(true)

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
                    val movie = MovieDetail(
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

                    withContext(Dispatchers.Main) {
                        onResult(movie)
                        setLoading(false)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onResult(null)
                        setLoading(false)
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onResult(null)
                setLoading(false)
            }
        }
    }
}

