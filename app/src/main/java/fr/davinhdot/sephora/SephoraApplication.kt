package fr.davinhdot.sephora

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SephoraApplication : Application() {

    override fun onCreate() {
        Timber.d("onCreate")
        super.onCreate()

        initTimber()
    }

    private fun initTimber() {

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}