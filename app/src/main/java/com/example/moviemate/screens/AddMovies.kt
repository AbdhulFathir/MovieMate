package com.example.moviemate.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.moviemate.CustomButton
import com.example.moviemate.MovieVM
import com.example.moviemate.R
import com.example.moviemate.db.Movie
import com.example.moviemate.ui.theme.MovieMateTheme

class AddMovies : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[MovieVM::class.java]
        setContent {
            MovieMateTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.bg_image),
                        contentDescription = "Background Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    val movies by viewModel.movies.observeAsState(emptyList())

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                            CustomButton(text = "Add Movies to DB") {
//                                val moviesToAdd = listOf(
//                                    Movie(
//                                        title = "Interstellar",
//                                        year = "2014",
//                                        rated = "PG-13",
//                                        released = "07 Nov 2014",
//                                        runtime = "169 min",
//                                        genre = "Adventure, Drama, Sci-Fi",
//                                        director = "Christopher Nolan",
//                                        writer = "Jonathan Nolan, Christopher Nolan",
//                                        actors = "Matthew McConaughey, Anne Hathaway, Jessica Chastain",
//                                        plot = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival."
//                                    ),
//                                    Movie(
//                                        title = "Fight Club",
//                                        year = "1999",
//                                        rated = "R",
//                                        released = "15 Oct 1999",
//                                        runtime = "139 min",
//                                        genre = "Drama",
//                                        director = "David Fincher",
//                                        writer = "Chuck Palahniuk, Jim Uhls",
//                                        actors = "Brad Pitt, Edward Norton, Meat Loaf",
//                                        plot = "An insomniac office worker and a devil-may-care soap maker form an underground fight club."
//                                    )
//                                )
//                                viewModel.addTodoItems(moviesToAdd)
//                            }
//
//                            Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn {
                            itemsIndexed(movies) { _, movie ->
                                MovieCard(movie = movie)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MovieCard(movie: Movie) {
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
            Text(text = movie.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "Year: ${movie.year}")
            Text(text = "Rated: ${movie.rated}")
            Text(text = "Released: ${movie.released}")
            Text(text = "Runtime: ${movie.runtime}")
            Text(text = "Genre: ${movie.genre}")
            Text(text = "Director: ${movie.director}")
            Text(text = "Actors: ${movie.actors}")
            Text(text = "Plot: ${movie.plot}", maxLines = 3)
        }
    }
}

