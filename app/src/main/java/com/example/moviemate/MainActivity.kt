package com.example.moviemate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.moviemate.ui.theme.MovieMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[MovieVM::class.java]

        enableEdgeToEdge()
        setContent {
            MovieMateTheme  {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MovieListView(viewModel, Modifier.padding(innerPadding))
                }
            }
        }
    }
}