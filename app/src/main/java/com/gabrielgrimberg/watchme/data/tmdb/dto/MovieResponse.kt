package com.gabrielgrimberg.watchme.data.tmdb.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieResponse(

    @SerializedName("page")
    @Expose
    var page: Int? = null,

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null,

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null,

    @SerializedName("results")
    @Expose
    var results: List<Movie>? = null
)