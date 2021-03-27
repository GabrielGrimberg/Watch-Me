package com.gabrielgrimberg.watchme

import android.app.Application
import com.gabrielgrimberg.watchme.data.tmdb.database.MovieDatabase
import com.gabrielgrimberg.watchme.data.firebase.FirebaseSource
import com.gabrielgrimberg.watchme.data.firebase.UserRepository
import com.gabrielgrimberg.watchme.ui.auth.factory.AuthViewModelFactory
import com.gabrielgrimberg.watchme.ui.auth.factory.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

lateinit var database: MovieDatabase

class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { FirebaseSource() }
        bind() from singleton { UserRepository(instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { MainViewModelFactory(instance()) }
    }

    companion object {
        lateinit var INSTANCE: App
    }

    init {
        INSTANCE = this
    }

    lateinit var cicerone: Cicerone<Router>

    override fun onCreate() {
        super.onCreate()
        database = MovieDatabase.getInstance(this)
        INSTANCE = this
        this.initCicerone()
    }

    private fun initCicerone() {
        this.cicerone = Cicerone.create()
    }
}