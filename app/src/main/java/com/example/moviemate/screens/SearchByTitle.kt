package com.example.moviemate.screens

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class SearchByTitle : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieMateTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var movieTitle by remember { mutableStateOf("") }
                    var isLoading by rememberSaveable { mutableStateOf(false) }
                    var results by remember { mutableStateOf<List<SimpleMovie>>(emptyList()) }

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
                                text = "Search By Title",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        Text("Search By Title", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = movieTitle,
                            onValueChange = { movieTitle = it },
                            label = { Text("Enter movie title") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        CustomButton(text = "Retrieve Movie") {
                            if (movieTitle.isNotBlank()) {
                                isLoading = true
                                fetchMovies(movieTitle, onResult = {
                                    results = it
                                }, setLoading = {
                                    isLoading = it
                                })
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                        }

                        if (results.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                items(results) { movie ->
                                    MovieCard(movie = movie)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: SimpleMovie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFBBDEFB)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = movie.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Year: ${movie.year}")
            Text("Imdb ID : ${movie.imdbID}")
            Text("Type: ${movie.type}")
//            Text("Plot: ${movie.Plot}")
        }
    }
}

private fun fetchMovies(
    title: String,
    onResult: (List<SimpleMovie>) -> Unit,
    setLoading: (Boolean) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        setLoading(true)

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

                    withContext(Dispatchers.Main) {
                        onResult(movieList)
                        setLoading(false)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onResult(emptyList())
                        setLoading(false)
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onResult(emptyList())
                setLoading(false)
            }
        }
    }
}


data class SimpleMovie(
    val title: String,
    val year: String,
    val imdbID: String,
    val type: String,
    val poster: String
)
