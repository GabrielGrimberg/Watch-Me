package com.gabrielgrimberg.watchme.ui.home.viewmodel

import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.gabrielgrimberg.watchme.data.tmdb.dto.Movie
import com.gabrielgrimberg.watchme.data.tmdb.repository.DefaultMovieRepository
import com.gabrielgrimberg.watchme.data.tmdb.repository.MovieRepository
import com.gabrielgrimberg.watchme.data.firebase.UserRepository
import com.gabrielgrimberg.watchme.ui.common.util.startAddMovieActivity
import com.gabrielgrimberg.watchme.ui.common.util.startLoginActivity

class MainViewModel(
    private val userRepository: UserRepository,
    private val repository: MovieRepository = DefaultMovieRepository()
) : ViewModel() {

    val user by lazy { userRepository.currentUser() }

    private val allMovies = MediatorLiveData<List<Movie>>()

    init {
        getAllMovies()
    }

    fun getSavedMovies() = allMovies

    private fun getAllMovies() {
        allMovies.addSource(repository.getSavedMovies()) { movies ->
            allMovies.postValue(movies)
        }
    }

    fun deleteSavedMovies(movie: Movie) {
        repository.deleteMovie(movie)
    }

    fun goToAddMovieActivity(view: View) {
        view.context.startAddMovieActivity()
    }

    fun logout(view: View){
        userRepository.logout()
        view.context.startLoginActivity()
    }
}