package com.gabrielgrimberg.watchme.ui.auth.listener

interface AuthListener {

    fun onStarted()

    fun onSuccess()

    fun onFailure(message: String)
}