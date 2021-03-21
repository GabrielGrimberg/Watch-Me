package com.gabrielgrimberg.watchme.ui.home.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gabrielgrimberg.watchme.R
import com.gabrielgrimberg.watchme.databinding.ActivityMainBinding
import com.gabrielgrimberg.watchme.ui.auth.activity.LoginActivity
import com.gabrielgrimberg.watchme.ui.movie.adapter.MovieListAdapter
import com.gabrielgrimberg.watchme.ui.auth.factory.MainViewModelFactory
import com.gabrielgrimberg.watchme.ui.movie.activity.AddMovieActivity
import com.gabrielgrimberg.watchme.ui.movie.activity.BaseActivity
import com.gabrielgrimberg.watchme.ui.home.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.moviesRecyclerView
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.toolbar_view.toolbar_toolbar_view
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity: BaseActivity(), KodeinAware {

    override val kodein by kodein()
    private val mainViewModelFactory : MainViewModelFactory by instance()

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    private val adapter = MovieListAdapter(mutableListOf())
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isUserLoggedIn()

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        binding.viewmodel = mainViewModel

        moviesRecyclerView.adapter = adapter
        showLoading()
        mainViewModel.getSavedMovies().observe(this, Observer { movies ->
            hideLoading()
            movies?.let {
                adapter.setMovies(movies)
            }
        })
    }

    private fun showLoading() {
        moviesRecyclerView.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        moviesRecyclerView.isEnabled = true
        progressBar.visibility = View.GONE
    }

    private fun deleteMoviesClicked() {
        for (movie in adapter.selectedMovies) {
            mainViewModel.deleteSavedMovies(movie)
        }
    }

    private fun isUserLoggedIn() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity<LoginActivity>()
            finish()
        }
    }

    override fun getToolbarInstance(): Toolbar = toolbar

    fun goToAddActivity(view: View) = startActivity<AddMovieActivity>()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> this.deleteMoviesClicked()
            R.id.action_signout -> mainViewModel.logout(window.decorView.rootView)
            else -> toast(getString(R.string.error))
        }
        return super.onOptionsItemSelected(item)
    }
}