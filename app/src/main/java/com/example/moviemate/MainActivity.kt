package com.example.moviemate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.moviemate.db.Movie
import com.example.moviemate.screens.AddMovies
import com.example.moviemate.screens.SearchActors
import com.example.moviemate.screens.SearchByTitle
import com.example.moviemate.screens.SearchMovies
import com.example.moviemate.ui.theme.MovieMateTheme
import com.example.moviemate.widgets.MovieVM

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[MovieVM::class.java]

        enableEdgeToEdge()
        setContent {
            MovieMateTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
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
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                        Button(onClick = {
//
//                        }) {
//                            Text("Add Demo Movies")
//                        }


                        CustomButton(text = "Add Movies to DB") {
                            val movies = listOf(
                                Movie(
                                    title = "The Shawshank Redemption",
                                    year = "1994",
                                    rated = "R",
                                    released = "14 Oct 1994",
                                    runtime = "142 min",
                                    genre = "Drama",
                                    director = "Frank Darabont",
                                    writer = "Stephen King, Frank Darabont",
                                    actors = "Tim Robbins, Morgan Freeman, Bob Gunton",
                                    plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."
                                ),
                                Movie(
                                    title = "Batman: The Dark Knight Returns, Part 1",
                                    year = "2012",
                                    rated = "PG-13",
                                    released = "25 Sep 2012",
                                    runtime = "76 min",
                                    genre = "Animation, Action, Crime, Drama, Thriller",
                                    director = "Jay Oliva",
                                    writer = "Bob Kane, Frank Miller, Klaus Janson, Bob Goodman",
                                    actors = "Peter Weller, Ariel Winter, David Selby, Wade Williams",
                                    plot = "Batman has not been seen for ten years. A new breed of criminal ravages Gotham City, forcing 55-year-old Bruce Wayne back into the cape and cowl. But, does he still have what it takes to fight crime in a new era?"
                                ),
                                Movie(
                                    title = "The Lord of the Rings: The Return of the King",
                                    year = "2003",
                                    rated = "PG-13",
                                    released = "17 Dec 2003",
                                    runtime = "201 min",
                                    genre = "Action, Adventure, Drama",
                                    director = "Peter Jackson",
                                    writer = "J.R.R. Tolkien, Fran Walsh, Philippa Boyens",
                                    actors = "Elijah Wood, Viggo Mortensen, Ian McKellen",
                                    plot = "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring."
                                ),
                                Movie(
                                    title = "Inception",
                                    year = "2010",
                                    rated = "PG-13",
                                    released = "16 Jul 2010",
                                    runtime = "148 min",
                                    genre = "Action, Adventure, Sci-Fi",
                                    director = "Christopher Nolan",
                                    writer = "Christopher Nolan",
                                    actors = "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page",
                                    plot = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster."
                                ),
                                Movie(
                                    title = "The Matrix",
                                    year = "1999",
                                    rated = "R",
                                    released = "31 Mar 1999",
                                    runtime = "136 min",
                                    genre = "Action, Sci-Fi",
                                    director = "Lana Wachowski, Lilly Wachowski",
                                    writer = "Lilly Wachowski, Lana Wachowski",
                                    actors = "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
                                    plot = "When a beautiful stranger leads computer hacker Neo to a forbidding underworld, he discovers the shocking truth--the life he knows is the elaborate deception of an evil cyber-intelligence."
                                )
                            )
                            viewModel.addMovies(movies)
                            val intent = Intent(this@MainActivity, AddMovies::class.java)
                            startActivity(intent)
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        CustomButton(text = "Search for Movies") {
                            val intent = Intent(this@MainActivity, SearchMovies::class.java)
                            startActivity(intent)
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        CustomButton(text = "Search for Actors") {
                            val intent = Intent(this@MainActivity, SearchActors::class.java)
                            startActivity(intent)
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        CustomButton(text = "Search by Title ") {
                            val intent = Intent(this@MainActivity, SearchByTitle::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CustomButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(300.dp)
            .height(50.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text, fontSize = 18.sp)
    }
}


