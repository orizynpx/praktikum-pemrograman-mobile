package io.github.orizynpx.fivegamesxml

import android.app.Application
import io.github.orizynpx.fivegamesxml.data.MovieRepository
import io.github.orizynpx.fivegamesxml.data.local.AppDatabase
import io.github.orizynpx.fivegamesxml.data.remote.NetworkClient
import timber.log.Timber

class FiveGamesApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val apiService by lazy { NetworkClient.apiService }
    val repository: MovieRepository by lazy { MovieRepository(apiService, database.movieDao()) }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
