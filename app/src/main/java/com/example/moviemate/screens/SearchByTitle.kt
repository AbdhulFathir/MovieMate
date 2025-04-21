package com.example.moviemate.screens

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.moviemate.widgets.SearchByTitleViewModel


class SearchByTitle : ComponentActivity() {
    private val viewModel: SearchByTitleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieMateTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val movieTitle by viewModel.movieTitle.collectAsState()
                    val isLoading by viewModel.isLoading.collectAsState()
                    val results by viewModel.results.collectAsState()

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

                        Image(
                            painter = painterResource(id = R.drawable.movie_mate_logo),
                            contentDescription = "MovieMate Logo",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(vertical = 16.dp),
                            contentScale = ContentScale.Fit
                        )

                        Text("Search By Title", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = movieTitle,
                            onValueChange = { viewModel.setMovieTitle(it) },
                            label = { Text("Enter movie title") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        CustomButton(text = "Retrieve Movie") {
                            if (movieTitle.isNotBlank()) {
                                viewModel.fetchMovies()
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



data class SimpleMovie(
    val title: String,
    val year: String,
    val imdbID: String,
    val type: String,
    val poster: String
)
