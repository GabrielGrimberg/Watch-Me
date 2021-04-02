package com.gabrielgrimberg.watchme.ui.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.gabrielgrimberg.watchme.R
import com.gabrielgrimberg.watchme.data.tmdb.dto.Movie
import com.gabrielgrimberg.watchme.data.tmdb.network.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_search.view.searchTitleTextView
import kotlinx.android.synthetic.main.item_movie_search.view.searchReleaseDateTextView
import kotlinx.android.synthetic.main.item_movie_search.view.searchImageView

class SearchMovieListAdapter(private val movies: MutableList<Movie>,
                             private var listener: (Movie) -> Unit) : RecyclerView.Adapter<SearchMovieListAdapter.MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_search, parent, false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movies[position], position)
    }

    fun setMovies(movieList: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movieList)
        notifyDataSetChanged()
    }

    inner class MovieHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie, position: Int) = with(view) {
            searchTitleTextView.text = movie.title
            searchReleaseDateTextView.text = movie.releaseDate

            view.setOnClickListener { listener(movies?.get(position)) }

            if (movie.posterPath != null)
                Picasso.get().load(RetrofitClient.TMDB_IMAGE_URL + movie.posterPath).into(searchImageView)
            else {
                searchImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_movie_24, null))
            }
        }
    }
}