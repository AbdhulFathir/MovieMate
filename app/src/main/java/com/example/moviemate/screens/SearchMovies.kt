package com.example.moviemate.screens

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.moviemate.widgets.MovieVM
import com.example.moviemate.R
import com.example.moviemate.ui.theme.MovieMateTheme
import com.example.moviemate.widgets.SearchMoviesViewModel

class SearchMovies : ComponentActivity() {

    private val searchViewModel: SearchMoviesViewModel by viewModels()
    private val databaseViewModel: MovieVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieMateTheme  {
                Surface(modifier = Modifier.fillMaxSize()) {

                    val movieTitle by searchViewModel.movieTitle.collectAsState()
                    val isLoading by searchViewModel.isLoading.collectAsState()
                    val movieDetail by searchViewModel.movieDetail.collectAsState()
                    val scrollState = rememberScrollState()

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

                        Column(
                            modifier = Modifier
                                .verticalScroll(scrollState),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Search Movie from OMDb API", fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = movieTitle,
                                onValueChange = { searchViewModel.setMovieTitle(it) },
                                label = { Text("Enter movie title") }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            CustomButton(text = "Retrieve Movie") {
                                searchViewModel.fetchMovieDetail()
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            CustomButton(text = "Save Movie to DB") {
                                movieDetail?.let { movie ->
                                    databaseViewModel.addMovie(
                                        title = movie.Title,
                                        year = movie.Year,
                                        rated = movie.Rated,
                                        released = movie.Released,
                                        runtime = movie.Runtime,
                                        genre = movie.Genre,
                                        director = movie.Director,
                                        writer = movie.Writer,
                                        actors = movie.Actors,
                                        plot = movie.Plot
                                    )
                                }
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
                                        containerColor = Color(0xFFBBDEFB)
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
}

