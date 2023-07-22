package com.ourproject.moviesapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.ourproject.moviesapp.service.result.MovieResultEntity
import com.ourproject.moviesapp.ui.theme.MoviesAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.util.CoilUtils
import okhttp3.OkHttpClient

class MainActivity : ComponentActivity() {

//    private var movies by mutableStateOf<List<MovieEntity>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    LaunchedEffect(true) {
                        loadPopularMovies()
                    }
                  MovieList(movies)
                }
            }
        }
    }

    private var movies by mutableStateOf<List<MovieResultEntity.MovieEntity>>(emptyList())

    private suspend fun loadPopularMovies() {
        try {
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.movieApi.getPopularMovies(
                    page = 1,
                    apiKey = "dd1a34e8db923f17b136543601658cee"
                )
            }

            if (response.isSuccessful) {
                val movieResultEntity = response.body()
                movies = movieResultEntity?.searches ?: emptyList()
                Log.d("AG", "loadPopularMovies: here movie you get is $movieResultEntity")
                // Handle the movieResultEntity here
            } else {
                // Handle error
            }
        } catch (e: Exception) {
            // Handle exceptions
        }
    }
}

@Composable
fun MovieList(movies: List<MovieResultEntity.MovieEntity>) {
    LazyColumn {
        items(movies.size) { index ->
            MovieItem(movies[index])
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MovieItem(movie: MovieResultEntity.MovieEntity) {
//    val imageLoader = ImageLoader.Builder(LocalContext.current)
//        .componentRegistry { add(SvgDecoder(LocalContext.current)) }
//        .build()
    Log.d("TAG", "MovieItem result you get is $movie: ")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Movie poster
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w342${movie.posterPath}",
                builder = {
                    crossfade(true)
//                    placeholder(Color.Gray) // Placeholder color while loading
//                    error(Color.Red) // Color shown on error
                }
            ),
            contentDescription = "Movie Poster",
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Movie title
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
