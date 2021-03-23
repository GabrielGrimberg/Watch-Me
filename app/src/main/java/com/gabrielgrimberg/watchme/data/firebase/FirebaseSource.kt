package com.gabrielgrimberg.watchme.data.firebase

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable

class FirebaseSource {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    /**
     * Competable used to perform the auth. Once it is completed we are using the emitter to indicate
     * that the task is completed or has failed.
     */
    fun login(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun register(email: String, password: String) = Completable.create { emitter ->
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            }
        }
    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser
}