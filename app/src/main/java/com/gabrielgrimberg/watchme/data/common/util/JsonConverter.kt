package com.gabrielgrimberg.watchme.data.common.util

import androidx.room.TypeConverter
import org.json.JSONObject

class JsonConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun convertFromJsonToString(value: JSONObject) : String {
            return value.toString()
        }

        @TypeConverter
        @JvmStatic
        fun convertFromStringToJson(value: String) : JSONObject {
            return JSONObject(value)
        }
    }
}