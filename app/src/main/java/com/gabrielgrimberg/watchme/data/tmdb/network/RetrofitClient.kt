package com.gabrielgrimberg.watchme.data.tmdb.network

import com.gabrielgrimberg.watchme.data.tmdb.dto.MovieResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val movieApi: MovieApi

    companion object {
        private const val PATH_TO_API_KEYS = "api-keys"
        private const val TMDB_BASE_URL = "http://api.themoviedb.org/3/"
        const val TMDB_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    }

    init {
        System.loadLibrary(PATH_TO_API_KEYS)
    }

    init {
        val retrofitClient = Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()

        movieApi = retrofitClient.create(MovieApi::class.java)
    }

    private external fun getKeys() : String

    fun searchMovies(query: String): Call<MovieResponse> {
        return movieApi.searchMovie(getKeys(), query)
    }
}