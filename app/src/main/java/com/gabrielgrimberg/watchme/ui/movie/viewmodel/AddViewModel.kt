package com.gabrielgrimberg.watchme.ui.movie.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabrielgrimberg.watchme.data.tmdb.dto.Movie
import com.gabrielgrimberg.watchme.data.tmdb.repository.DefaultMovieRepository
import com.gabrielgrimberg.watchme.data.tmdb.repository.MovieRepository

class AddViewModel(private val repository: MovieRepository = DefaultMovieRepository()) : ViewModel() {

    var title = ObservableField("")
    var releaseDate = ObservableField("")

    // Signal the activity if a movie has been saved or not.
    private val saveLiveData = MutableLiveData<Boolean>()

    // Returns LiveData boolean that is updated with the values in saveLiveData.
    fun getSaveLiveData(): LiveData<Boolean> = saveLiveData

    // Uses the saveMovie() from the repository to save the movie in the database using the title and release date the user enters.
    // If the movie saves successfully the boolean will return a true, otherwise return false.
    fun saveMovie() {
        if (saveMovieFromUser()) {
            repository.saveMovie(Movie(title = title.get(), releaseDate = releaseDate.get()))
            saveLiveData.postValue(true)
        } else {
            saveLiveData.postValue(false)
        }
    }

    // Verify that the title is not empty.
    // View Model takes care of this and not the Activity.
    fun saveMovieFromUser(): Boolean {
        val title = this.title.get()
        val releaseDate = this.releaseDate.get()

        if (title != null && releaseDate != null) {
            return title.isNotEmpty() && releaseDate.isNotEmpty()
        }
        return false
    }
}