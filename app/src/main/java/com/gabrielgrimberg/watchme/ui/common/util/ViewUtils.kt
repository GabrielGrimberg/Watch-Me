package com.gabrielgrimberg.watchme.ui.common.util

import android.content.Context
import android.content.Intent
import com.gabrielgrimberg.watchme.ui.home.activity.MainActivity
import com.gabrielgrimberg.watchme.ui.auth.activity.LoginActivity
import com.gabrielgrimberg.watchme.ui.movie.activity.AddMovieActivity

fun Context.startHomeActivity() =
        Intent(this, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

fun Context.startLoginActivity() =
        Intent(this, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

fun Context.startAddMovieActivity() =
        Intent(this, AddMovieActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }