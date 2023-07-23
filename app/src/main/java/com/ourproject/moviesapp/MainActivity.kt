package com.ourproject.moviesapp

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ourproject.moviesapp.service.result.MovieResultEntity
import com.ourproject.moviesapp.ui.theme.MoviesAppTheme
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

class MainActivity : ComponentActivity() {

    private val movieViewModel: MovieViewModel by viewModels()

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    movieViewModel.setConnectivityStatus(ConnectivityStatus.Connected)
                }

                override fun onLost(network: Network) {
                    movieViewModel.setConnectivityStatus(ConnectivityStatus.Disconnected)
                }
            }

            connectivityManager.registerDefaultNetworkCallback(networkCallback)
            MoviesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Load popular movies using LaunchedEffect
                    LaunchedEffect(true) {
                        movieViewModel.loadPopularMovies()
                    }

                    MovieList(
                        movies = movieViewModel.moviesLiveData.collectAsState().value,
                        isLoading = movieViewModel.isLoading.collectAsState().value,
                        isRetryButtonVisible = movieViewModel.isRetryButtonVisible.collectAsState().value,
                        viewModel = movieViewModel,
                        onRetryButtonClick = {
                            movieViewModel.loadPopularMovies()
                        }
                    )
                }
            }
        }
    }





}

@Composable
fun SimulateNotConnected(retryOnClick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No Connection.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
            Button(onClick = { retryOnClick() }) {
                Text(text = "Retry")
            }

        }
    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MovieList(movies: List<MovieResultEntity.MovieEntity>, isLoading: Boolean,
              isRetryButtonVisible: Boolean,
              viewModel: MovieViewModel,
              onRetryButtonClick: () -> Unit
              ) {

    val connectivityStatus = viewModel.connectivityStatus.value
//
    if (connectivityStatus == ConnectivityStatus.Disconnected) {
        SimulateNotConnected(retryOnClick = { viewModel.loadPopularMovies() })
    } else {

        val isRefreshing = isLoading

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.loadPopularMovies() }
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                LazyColumn {
                    items(movies.size) { index ->
                        MovieItem(movies[index])
                    }
                }

                if (isRetryButtonVisible) {
                    Button(
                        onClick = { onRetryButtonClick() },
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Text(text = "Retry")
                    }
                }
            }
        }

    }



}

@Composable
fun MovieItem(movie: MovieResultEntity.MovieEntity) {

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
