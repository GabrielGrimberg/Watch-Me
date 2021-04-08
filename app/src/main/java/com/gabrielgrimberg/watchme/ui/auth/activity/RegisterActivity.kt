package com.gabrielgrimberg.watchme.ui.auth.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gabrielgrimberg.watchme.R
import com.gabrielgrimberg.watchme.databinding.ActivityRegisterBinding
import com.gabrielgrimberg.watchme.ui.home.activity.MainActivity
import com.gabrielgrimberg.watchme.ui.auth.factory.AuthViewModelFactory
import com.gabrielgrimberg.watchme.ui.auth.listener.AuthListener
import com.gabrielgrimberg.watchme.ui.auth.viewmodel.AuthViewModel
import com.gabrielgrimberg.watchme.ui.common.util.startHomeActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class RegisterActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by kodein()
    private val authViewModelFactory : AuthViewModelFactory by instance()

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val binding = DataBindingUtil.setContentView<ActivityRegisterBinding>(this, R.layout.activity_register)
        authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)
        binding.viewmodel = authViewModel
        authViewModel.authListener = this
    }

    override fun onStarted() {
        progressbar.visibility = View.VISIBLE
        Intent(this, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        startHomeActivity()
    }

    override fun onFailure(message: String) {
        progressbar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}