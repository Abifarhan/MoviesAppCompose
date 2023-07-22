package com.ourproject.moviesapp

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ourproject.moviesapp.service.MovieApi
import com.ourproject.moviesapp.service.result.MovieResultEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository () {

//    private val apiService: MovieApiService
//
//    init {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.themoviedb.org/3/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        apiService = retrofit.create(MovieApiService::class.java)
//    }
//
//    suspend fun getPopularMovies(): MovieResultEntity {
//        return apiService.getPopularMovies()
//    }
}