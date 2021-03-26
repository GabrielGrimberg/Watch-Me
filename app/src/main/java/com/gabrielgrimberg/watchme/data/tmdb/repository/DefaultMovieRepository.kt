package com.gabrielgrimberg.watchme.data.tmdb.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gabrielgrimberg.watchme.data.tmdb.database.MovieDao
import com.gabrielgrimberg.watchme.data.tmdb.dto.Movie
import com.gabrielgrimberg.watchme.data.tmdb.dto.MovieResponse
import com.gabrielgrimberg.watchme.data.tmdb.network.RetrofitClient
import com.gabrielgrimberg.watchme.database
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class DefaultMovieRepository : MovieRepository {

    private val movieDao: MovieDao = database.movieDao()
    private val retrofitClient = RetrofitClient()
    private val allMovies: LiveData<List<Movie>> = movieDao.getAll()

    override fun deleteMovie(movie: Movie) {
        thread {
            database.movieDao().delete(movie.id)
        }
    }

    override fun getSavedMovies() = allMovies

    override fun saveMovie(movie: Movie) {
        thread {
            movieDao.insert(movie)
        }
    }

    override fun searchMovies(query: String): LiveData<List<Movie>?> {
        val listOfMovies = MutableLiveData<List<Movie>>()

        retrofitClient.searchMovies(query).enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                listOfMovies.value = null
                Log.d(this.javaClass.simpleName, "Failure")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                listOfMovies.value = response.body()?.results
                Log.d(this.javaClass.simpleName, "Response: ${response.body()?.results}")
            }
        })
        return listOfMovies
    }
}