package com.ourproject.moviesapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ourproject.moviesapp.service.result.MovieResultEntity
import kotlinx.coroutines.launch
import java.lang.Exception
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.ourproject.moviesapp.service.MovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieViewModel: ViewModel() {

    private val _moviesLiveData = mutableStateOf<List<MovieResultEntity.MovieEntity>>(emptyList())
    val moviesLiveData: State<List<MovieResultEntity.MovieEntity>> = _moviesLiveData

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isRetryButtonVisible = mutableStateOf(false)
    val isRetryButtonVisible: State<Boolean> = _isRetryButtonVisible

    private val _retryCount = mutableStateOf(0)
    val retryCount: State<Int> = _retryCount

    private val _connectivityStatus = mutableStateOf(ConnectivityStatus.Connected)
    val connectivityStatus: State<ConnectivityStatus> = _connectivityStatus

    fun setConnectivityStatus(status: ConnectivityStatus) {
        _connectivityStatus.value = status
    }

    fun loadPopularMovies() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _isRetryButtonVisible.value = false


                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.movieApi.getPopularMovies(
                        page = 1,
                        apiKey = "dd1a34e8db923f17b136543601658cee"
                    )
                }

                if (response.isSuccessful) {
                    val movieResultEntity = response.body()
                    _moviesLiveData.value = movieResultEntity?.searches ?: emptyList()
                } else {
                    // Handle error and show retry button
                    _isRetryButtonVisible.value = true
                }

            } catch (e: Exception) {
                // Handle exceptions and show retry button
                _isRetryButtonVisible.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun incrementRetryCount() {
        _retryCount.value += 1
    }
}


enum class ConnectivityStatus {
    Connected,
    Disconnected
}