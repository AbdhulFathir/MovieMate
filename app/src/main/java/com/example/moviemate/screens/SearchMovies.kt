package com.example.moviemate.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviemate.CustomButton
import com.example.moviemate.ui.theme.MovieMateTheme

class SearchMovies : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieMateTheme  {
                Scaffold(

                    //app bar
                    topBar = {
                        TopAppBar(
                            title = {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "Search Movies",
                                        style = androidx.compose.ui.text.TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        )
                    },


                    // body part
                    content = { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            var movieTitle by remember { mutableStateOf("") }

                            Text("Search Movie from OMDb API", fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = movieTitle,
                                onValueChange = { movieTitle = it },
                                label = { Text("Enter movie title") }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            CustomButton(text = "Retrieve Movie") {
                                // TODO: Fetch from API
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            CustomButton(text = "Save Movie to DB") {
                                // TODO: Save to Room
                            }
                        }
                    }
                )
            }
        }
    }
}
