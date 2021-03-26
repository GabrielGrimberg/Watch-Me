package com.gabrielgrimberg.watchme.data.tmdb.network

import com.gabrielgrimberg.watchme.data.tmdb.dto.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("search/movie")
    fun searchMovie(@Query("api_key") api_key: String, @Query("query") query: String): Call<MovieResponse>
}