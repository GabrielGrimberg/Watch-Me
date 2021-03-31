package com.gabrielgrimberg.watchme.ui.movie.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gabrielgrimberg.watchme.R
import com.gabrielgrimberg.watchme.ui.common.util.action
import com.gabrielgrimberg.watchme.data.tmdb.dto.Movie
import com.gabrielgrimberg.watchme.ui.common.util.snack
import com.gabrielgrimberg.watchme.ui.home.activity.MainActivity
import com.gabrielgrimberg.watchme.ui.movie.adapter.SearchMovieListAdapter
import com.gabrielgrimberg.watchme.ui.movie.viewmodel.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search_movie.searchRecyclerView
import kotlinx.android.synthetic.main.activity_search_movie.searchProgressBar
import kotlinx.android.synthetic.main.activity_search_movie.searchMovieLayout
import kotlinx.android.synthetic.main.toolbar_view.toolbar_toolbar_view
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SearchMovieActivity : BaseActivity() {

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    private var searchMovieAdapter = SearchMovieListAdapter(mutableListOf()) { movie -> displayConfirmation(movie) }

    private lateinit var viewModel: SearchViewModel

    private lateinit var title: String

    override fun getToolbarInstance(): Toolbar? = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)
        intent?.extras?.getString("title")?.let {
            title = it
        }
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchRecyclerView.adapter = searchMovieAdapter
        searchMovie()
    }

    private fun showLoading() {
        searchProgressBar.visibility = View.VISIBLE
        searchRecyclerView.isEnabled = false
    }

    private fun hideLoading() {
        searchProgressBar.visibility = View.GONE
        searchRecyclerView.isEnabled = true
    }

    private fun showMessage() {
        searchMovieLayout.snack(getString(R.string.network_error), Snackbar.LENGTH_INDEFINITE) {
            action(getString(R.string.ok)) {
                searchMovie()
            }
        }
    }

    private fun displayConfirmation(movie: Movie) {
        searchMovieLayout.snack("Add ${movie.title} to your list?", Snackbar.LENGTH_LONG) {
            action(getString(R.string.ok)) {
                viewModel.saveMovie(movie)
                startActivity(intentFor<MainActivity>().newTask().clearTask())
            }
        }
    }

    private fun searchMovie() {
        showLoading()
        viewModel.searchMovie(title).observe(this, Observer { movies ->
            hideLoading()
            if (movies == null) {
                showMessage()
            } else {
                searchMovieAdapter.setMovies(movies)
            }
        })
    }
}