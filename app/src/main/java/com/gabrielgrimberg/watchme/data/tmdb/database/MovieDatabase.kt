package com.gabrielgrimberg.watchme.data.tmdb.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gabrielgrimberg.watchme.data.tmdb.dto.Movie
import com.gabrielgrimberg.watchme.data.common.util.GenreIdConverter

@Database(entities = [Movie::class], version = 1)
@TypeConverters(GenreIdConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private val lock = Any()
        private const val DATABASE_NAME = "MovieDatabase"

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(application: Application): MovieDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(application, MovieDatabase::class.java, DATABASE_NAME)
                            .build()
                }
            }
            return INSTANCE!! // !! Returns NullPointerException
        }
    }
}