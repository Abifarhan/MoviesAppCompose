package com.ourproject.moviesapp.service

import com.ourproject.moviesapp.service.result.MovieResultEntity
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = "dd1a34e8db923f17b136543601658cee",
        @Query("language") language: String = "en"
    ): Response<MovieResultEntity>
}