package com.gabrielgrimberg.watchme.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gabrielgrimberg.watchme.data.tmdb.dto.Movie
import com.gabrielgrimberg.watchme.data.tmdb.repository.DefaultMovieRepository
import com.gabrielgrimberg.watchme.data.tmdb.repository.MovieRepository

class SearchViewModel(private val repository: MovieRepository = DefaultMovieRepository()): ViewModel()  {

    fun searchMovie(query: String): LiveData<List<Movie>?> {
        return repository.searchMovies(query)
    }

    fun saveMovie(movie: Movie) {
        repository.saveMovie(movie)
    }
}