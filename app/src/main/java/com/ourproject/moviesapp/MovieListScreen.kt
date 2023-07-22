package com.ourproject.moviesapp

import androidx.compose.runtime.mutableStateOf

class MovieListScreen(apikey: String) {
    var movieList by remeber { mutableStateOf<List<Movie>> }
}