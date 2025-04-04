package com.example.moviemate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviemate.db.Movie
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MovieListView(viewModel: MovieVM, modifier: Modifier = Modifier) {
    val movieList by viewModel.movies.observeAsState()

    var inputText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxSize().padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(value = inputText, onValueChange = {
                inputText = it
            })

            Button(onClick = {
                viewModel.addTodoItem(title = inputText,
                    actors = "Some Actor",
                    director = "Some Director",
                    genre = "Action",
                    plot = "Sample plot",
                    rated = "PG-13",
                    released = "2022-01-01",
                    runtime = "120 min",
                    writer = "Some Writer",
                    year = "2022")
                inputText = ""
            }) {
                Text("Add")
            }
        }

        Button(onClick = {
            val movies = listOf(
                Movie(title = "Inception", year = "2010", rated = "PG-13", released = "2010-07-16", runtime = "148 min", genre = "Sci-Fi", director = "Christopher Nolan", writer = "Nolan", actors = "DiCaprio", plot = "Dream within dreams"),
                Movie(title = "The Matrix", year = "1999", rated = "R", released = "1999-03-31", runtime = "136 min", genre = "Sci-Fi", director = "Wachowski Bros", writer = "Wachowski Bros", actors = "Keanu Reeves", plot = "Virtual reality truth")
            )
            viewModel.addTodoItems(movies)
        }) {
            Text("Add Demo Movies")
        }


        movieList?.let {
            LazyColumn(content = {
                itemsIndexed(it) { _, item ->
                    TodoItem(item) {
                        viewModel.deleteTodoItem(item.id)
                    }
                }
            })
        }
    }
}

@Composable
fun TodoItem(todo: Movie, onDelete: ()-> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = todo.title,
                fontSize = 25.sp,
                color = Color.White
            )

            Text(
                text = SimpleDateFormat("HH:mm:aa, dd/mm", Locale.ENGLISH).format(todo.id),
                fontSize = 12.sp,
                color = Color.LightGray
            )
        }

        Column {
            IconButton(onClick = {
                onDelete()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }

            IconButton(onClick = {
                // perform update
                // display an alert and get updated todo title from user
                // on confirm - save it to room DB
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_draw_24),
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }
    }
}