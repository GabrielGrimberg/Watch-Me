package com.gabrielgrimberg.watchme.data.tmdb.repository

import androidx.lifecycle.LiveData
import com.gabrielgrimberg.watchme.data.tmdb.dto.Movie

interface MovieRepository {

    fun getSavedMovies(): LiveData<List<Movie>>

    fun saveMovie(movie: Movie)

    fun deleteMovie(movie: Movie)

    fun searchMovies(query: String): LiveData<List<Movie>?>
}