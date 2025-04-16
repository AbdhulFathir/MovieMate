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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.moviemate.CustomButton
import com.example.moviemate.MovieVM
import com.example.moviemate.R
import com.example.moviemate.ui.theme.MovieMateTheme

class SearchActors : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieMateTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var actorQuery by rememberSaveable { mutableStateOf("") }
                    val viewModel = ViewModelProvider(this)[MovieVM::class.java]
                    val movieResults by viewModel.searchByActor(actorQuery).observeAsState(emptyList())

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
                                text = "Search Actors",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }



                        Text("Search Movies by Actor", fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = actorQuery,
                            onValueChange = { actorQuery = it },
                            label = { Text("Enter actor name") }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

//                        CustomButton(text = "Search") {
//                        }

                        if(actorQuery.isNotEmpty()){
                            LazyColumn {
                                items(movieResults) { movie ->
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

