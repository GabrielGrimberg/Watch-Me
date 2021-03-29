package com.gabrielgrimberg.watchme.ui.movie.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * BaseActivity to have all shared functions within all Activities, it's like a common.
 * Java considers this to be an Anti-Pattern due to a Class can only extend one other Class.
 *
 * Helper Class can be considered a better alternative to BaseActivity.
 * The Helper can be instantiated in the onCreate() of an Activity.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        this.getToolbarInstance()?.let { this.initView(it) }
    }

    private fun initView(toolbar: Toolbar) = setSupportActionBar(toolbar)

    abstract fun getToolbarInstance(): Toolbar?
}