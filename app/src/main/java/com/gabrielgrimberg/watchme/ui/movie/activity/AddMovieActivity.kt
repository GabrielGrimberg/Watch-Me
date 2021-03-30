package com.gabrielgrimberg.watchme.ui.movie.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gabrielgrimberg.watchme.R
import com.gabrielgrimberg.watchme.ui.common.util.action
import com.gabrielgrimberg.watchme.databinding.ActivityAddMovieBinding
import com.gabrielgrimberg.watchme.ui.common.util.snack
import com.gabrielgrimberg.watchme.ui.movie.viewmodel.AddViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_movie.titleEditText
import kotlinx.android.synthetic.main.activity_add_movie.addLayout
import kotlinx.android.synthetic.main.toolbar_view.toolbar_toolbar_view
import org.jetbrains.anko.intentFor

class AddMovieActivity : BaseActivity() {

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }
    private lateinit var viewModel: AddViewModel

    override fun getToolbarInstance(): Toolbar? = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout and get a reference to the binding.
        val binding = DataBindingUtil.setContentView<ActivityAddMovieBinding>(this, R.layout.activity_add_movie)
        // Creates the reference to AddViewModel.
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        // Assigns the View Model to the binding.
        binding.viewModel = viewModel
        configureLiveDataObservers()
    }

    fun searchMovieClicked(view: View) {
        if (titleEditText.text.toString().isNotBlank()) {
            startActivity(intentFor<SearchMovieActivity>("title" to titleEditText.text.toString()))
        } else {
            showMessage(getString(R.string.enter_title))
        }
    }

    private fun showMessage(msg: String) {
        addLayout.snack((msg), Snackbar.LENGTH_LONG) {
            action(getString(R.string.ok)) {
            }
        }
    }

    /**
     * Observer to the ViewModel when a movie gets saved.
     * Sets up an Observer on saveLiveData from the viewModel to close the activity.
     */
    private fun configureLiveDataObservers() {
        viewModel.getSaveLiveData().observe(this, Observer { saved ->
            saved?.let {
                if (saved) {
                    finish()
                } else {
                    showMessage(getString(R.string.enter_title))
                }
            }
        })
    }
}