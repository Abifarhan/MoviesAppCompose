package com.ourproject.moviesapp

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

//    private val movieApi: MovieApi = createRetrofit().create(MovieApi::class.java)
//
//    private val _moviesLiveData = MutableLiveData<List<Movie>>()
//    val moviesLiveData: LiveData<List<Movie>> get() = _moviesLiveData
//
//    fun getPopularMovies() {
//        viewModelScope.launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    movieApi.getPopularMovies(apiKey = "dd1a34e8db923f17b136543601658cee", language = "en", page = 1)
//                }
//
//                if (response.isSuccessful) {
//                    val movies = response.body()?.results ?: emptyList()
//                    _moviesLiveData.value = movies
//                } else {
//                    // Handle error
//                }
//            } catch (e: Exception) {
//                // Handle exception
//            }
//        }
//    }
//
//    fun getPopularMovies2() {
//
//        try {
//            val response = withContext(Dispatchers.IO) {
//                movieApi.getPopularMovies(apiKey = "YOUR_API_KEY_HERE", language = "en", page = 1)
//            }
//
//            if (response.isSuccessful) {
//                val movies = response.body()?.results ?: emptyList()
//                _moviesLiveData.postValue(movies)
//            } else {
//                // Handle error
//            }
//        } catch (e: Exception) {
//            // Handle exception
//        }
//    }
//
//    fun createRetrofit(): Retrofit {
//        val baseUrl = "https://api.themoviedb.org/3/"
//        val apiKey = "YOUR_API_KEY_HERE"
//
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
}