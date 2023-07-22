package com.ourproject.moviesapp

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ourproject.moviesapp.service.result.MovieResultEntity
import timber.log.Timber
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.paging.compose.collectAsLazyPagingItems


@Composable
fun MovieListScreen(
    movieList: List<MovieResultEntity.MovieEntity>
) {
    LazyColumn {
        Log.d("TAG", "MovieListScreen: data you get is 1")
        itemsIndexed(movieList) { index, movie ->

            Log.d("TAG", "MovieListScreen: data you get is $movie")
            // Display each movie item here
            // Example: Text(movie.title)
            // You can access other properties of the movie entity as needed.
        }
    }
}