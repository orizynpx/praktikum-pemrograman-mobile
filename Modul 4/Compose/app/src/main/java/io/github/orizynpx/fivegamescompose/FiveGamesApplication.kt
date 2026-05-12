package io.github.orizynpx.fivegamescompose

import android.app.Application
import timber.log.Timber

class FiveGamesApplication  : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}