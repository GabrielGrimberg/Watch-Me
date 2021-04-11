package com.gabrielgrimberg.watchme.ui.auth.viewmodel

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.gabrielgrimberg.watchme.data.firebase.UserRepository
import com.gabrielgrimberg.watchme.ui.auth.activity.LoginActivity
import com.gabrielgrimberg.watchme.ui.auth.activity.RegisterActivity
import com.gabrielgrimberg.watchme.ui.auth.listener.AuthListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {

    // Inputs from View
    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    private val disposeCompletables = CompositeDisposable()

    val user by lazy { userRepository.currentUser() }

    fun performLogin() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        authListener?.onStarted()

        val disposableToPerformActualAuthentication = userRepository.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposeCompletables.add(disposableToPerformActualAuthentication)
    }

    fun performRegister() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Please input all values")
            return
        }
        authListener?.onStarted()
        val disposableToPerformUserRegistration = userRepository.register(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposeCompletables.add(disposableToPerformUserRegistration)
    }

    fun goToRegisterPage(view: View) {
        Intent(view.context, RegisterActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToLoginPage(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposeCompletables.dispose()
    }
}