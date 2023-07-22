package com.ourproject.moviesapp

import com.ourproject.moviesapp.service.MovieApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "dd1a34e8db923f17b136543601658cee"

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val movieApi: MovieApi = retrofit.create(MovieApi::class.java)

    // Helper function to generate the Authorization header
    fun generateAuthorizationHeader(apiKey: String): String = "Bearer $apiKey"

    // Helper function to get the Retrofit service
    fun getMovieApiService(): MovieApi = movieApi

}