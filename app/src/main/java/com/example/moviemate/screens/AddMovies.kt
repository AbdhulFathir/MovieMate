package com.example.moviemate.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviemate.MovieVM
import com.example.moviemate.ui.theme.MovieMateTheme

class AddMovies : ComponentActivity() {
    private lateinit var viewModel: MovieVM
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieMateTheme {
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
                                        "Movies List",
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
                            Text("Add Hardcoded Movies to Local DB", fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(24.dp))

                            Button(onClick = {}) {
                                Text("Add Movies")
                            }
                        }
                    }
                )
            }
        }
    }
}


