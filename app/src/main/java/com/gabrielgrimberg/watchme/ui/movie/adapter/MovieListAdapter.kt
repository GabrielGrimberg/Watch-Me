package com.gabrielgrimberg.watchme.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gabrielgrimberg.watchme.R
import com.gabrielgrimberg.watchme.data.tmdb.dto.Movie
import com.gabrielgrimberg.watchme.data.tmdb.network.RetrofitClient
import com.gabrielgrimberg.watchme.databinding.ItemMovieMainBinding
import com.gabrielgrimberg.watchme.ui.common.util.setImageUrl
import java.util.HashSet

class MovieListAdapter(private val movies: MutableList<Movie>)
    : RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

    val selectedMovies = HashSet<Movie>()

    /***
     * Create the binding objects as soon as the layouts are inflated.
     * With an Activity, this means adding the code in onCreate()
     * With an Adapter, the code goes in onCreateViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        // Inflate the View and bind the item_movie_main.
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMovieMainBinding>(layoutInflater, R.layout.item_movie_main, parent, false)
        return MovieHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    /**
     * Item Movie Main is now bound to the layout.
     * Now we must tell the object which movie to display using the onBindViewHolder()
     * Method takes a holder and the current position in the list.
     */
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        // Get the movie object from the list of movies that should be displayed.
        val movie = movies[position]

        // Tell the layout which movie object is going to be bound to the layout.
        holder.binding.movie = movie

        if (movie.posterPath != null) {
            // Load image if available from the API.
            holder.binding.movieImageView.setImageUrl(RetrofitClient.TMDB_IMAGE_URL + movie.posterPath)
        } else {
            // Load default image using a drawable.
            holder.binding.movieImageView.setImageUrl(R.drawable.ic_movie_24)
        }

        // Assigning the correct state for the checkbox based on user's actions.
        holder.binding.checkbox.isChecked = selectedMovies.contains(movie)

        // Listener method used to add the watched movies to the list in case of deletion.
        holder.binding.checkbox.setOnCheckedChangeListener{ checkbox, isChecked ->
            if (!selectedMovies.contains(movie) && isChecked) {
                selectedMovies.add(movies[position])
            } else {
                selectedMovies.remove(movies[position])
            }
        }
    }

    fun setMovies(movieList: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movieList)
        notifyDataSetChanged()
    }

    /**
     * Binding the data source to the layout.
     * MovieHolder expects an ItemMovieMainBinding as a constructor parameter.
     * Data Binding library auto generates the classes required to bind the layout with the data objects.
     * Example: item_movie_main becomes ItemMovieMainBinding. The Binding suffix appended indicating it is Binding.
     */
    inner class MovieHolder(val binding: ItemMovieMainBinding) : RecyclerView.ViewHolder(binding.root)
}