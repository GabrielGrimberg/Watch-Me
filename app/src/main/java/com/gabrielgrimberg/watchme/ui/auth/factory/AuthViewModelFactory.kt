package com.gabrielgrimberg.watchme.ui.auth.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gabrielgrimberg.watchme.data.firebase.UserRepository
import com.gabrielgrimberg.watchme.ui.auth.viewmodel.AuthViewModel

/**
 * UserRepository required to be used in AuthViewModel
 * This Factory is to generate the ViewModel with the required parameter.
 */
@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(userRepository) as T
    }
}