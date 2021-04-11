package com.gabrielgrimberg.watchme.ui.auth.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gabrielgrimberg.watchme.data.firebase.UserRepository
import com.gabrielgrimberg.watchme.ui.home.viewmodel.MainViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
        private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(userRepository) as T
    }
}