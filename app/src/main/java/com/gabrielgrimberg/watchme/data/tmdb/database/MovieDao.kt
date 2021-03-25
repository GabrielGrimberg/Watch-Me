package com.gabrielgrimberg.watchme.data.tmdb.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gabrielgrimberg.watchme.data.tmdb.dto.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Query("SELECT * FROM movie")
    fun getAll(): LiveData<List<Movie>>

    @Query("DELETE FROM movie WHERE watched = :watched")
    fun deleteMovies(watched: Boolean)

    @Update
    fun updateMovie(movie: Movie)

    @Query("DELETE FROM movie WHERE id = :id")
    fun delete(id: Int?)
}