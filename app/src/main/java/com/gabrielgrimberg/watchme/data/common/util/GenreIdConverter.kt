package com.gabrielgrimberg.watchme.data.common.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Collections

class GenreIdConverter {

    private val gson = Gson()

    @TypeConverter
    fun convertStringToGenreList(data: String?): List<Int> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun convertGenreListToString(listOfGenreIds: List<Int>?): String {
        if (listOfGenreIds == null) {
            return gson.toJson(Collections.emptyList<Int>())
        }
        return gson.toJson(listOfGenreIds)
    }
}