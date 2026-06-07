package io.github.orizynpx.fivemoviesxml

import android.app.Application
import io.github.orizynpx.fivemoviesxml.data.MovieRepository
import io.github.orizynpx.fivemoviesxml.data.local.AppDatabase
import io.github.orizynpx.fivemoviesxml.data.remote.NetworkClient
import timber.log.Timber

class FiveMoviesApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val apiService by lazy { NetworkClient.apiService }
    val repository: MovieRepository by lazy { MovieRepository(apiService, database.movieDao()) }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
