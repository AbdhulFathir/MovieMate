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

class SearchActors : ComponentActivity() {
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
                                        "Search Actors",
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
                            var actorQuery by remember { mutableStateOf("") }

                            Text("Search Movies by Actor", fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = actorQuery,
                                onValueChange = { actorQuery = it },
                                label = { Text("Enter actor name") }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            CustomButton(text = "Search") {
                                // TODO: Query Room DB for matching actor
                            }
                        }
                    }
                )
            }
        }
    }
}

