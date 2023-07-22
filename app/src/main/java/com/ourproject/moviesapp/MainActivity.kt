package com.ourproject.moviesapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
private val movieViewModel: MovieViewModel by viewModels()
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
                        movieViewModel.loadPopularMovies()
//                        loadPopularMovies()
                    }

                    val movies = movieViewModel.moviesLiveData.value
                    val isLoading = movieViewModel.isLoading.value
                    val isRetryButtonVisible = movieViewModel.isRetryButtonVisible.value
                    Log.d("TAG", "onCreate: simulate mock $isRetryButtonVisible")
                    MovieList(
                        movies = movies,
                        isLoading = isLoading,
//                        isRetryButtonVisible = isRetryButtonVisible,
                        isRetryButtonVisible = true,
                        onRetryButtonClick = { movieViewModel.loadPopularMovies() }
                    )
                }
            }
        }
    }

    private var movies by mutableStateOf<List<MovieResultEntity.MovieEntity>>(emptyList())
    private var isLoading by mutableStateOf(true)
    private var isRetryButtonVisible by mutableStateOf(false)

    private suspend fun loadPopularMovies() {
        try {
            isLoading = true
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.movieApi.getPopularMovies(
                    page = 1,
                    apiKey = "dd1a34e8db923f17b136543601658cee"
                )
            }

            if (response.isSuccessful) {
                val movieResultEntity = response.body()
                movies = movieResultEntity?.searches ?: emptyList()
                isLoading = false
                if (movies.isEmpty()) {
                    isRetryButtonVisible = true // Show the retry button
                } else {
                    isRetryButtonVisible = false // Hide the retry button if data is available
                }
                Log.d("AG", "loadPopularMovies: here movie you get is $movieResultEntity")
                // Handle the movieResultEntity here
            } else {
                isRetryButtonVisible = true

                // Handle error
            }
        } catch (e: Exception) {
            isRetryButtonVisible = true
            // Handle exceptions
        }
    }


}

@Composable
fun MovieList(movies: List<MovieResultEntity.MovieEntity>, isLoading: Boolean,
              isRetryButtonVisible: Boolean,
              onRetryButtonClick: () -> Unit
              ) {


    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center))
    } else {
        val simulateEmpty = MovieResultEntity.DEFAULT
        if (movies.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No movies found.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                    if (isRetryButtonVisible) {
                        Button(onClick = { onRetryButtonClick() }) {
                            Text(text = "Retry")
                        }
                    }
                }
            }
        } else {
            LazyColumn {
                items(movies.size) { index ->
                    MovieItem(movies[index])
                }
            }
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
