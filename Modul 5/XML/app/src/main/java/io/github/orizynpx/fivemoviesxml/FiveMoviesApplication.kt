package io.github.orizynpx.fivemoviesxml

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import io.github.orizynpx.fivemoviesxml.data.MovieRepository
import io.github.orizynpx.fivemoviesxml.data.local.AppDatabase
import io.github.orizynpx.fivemoviesxml.data.remote.NetworkClient
import timber.log.Timber

class FiveMoviesApplication : Application(), SingletonImageLoader.Factory {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val apiService by lazy { NetworkClient.apiService }
    val repository: MovieRepository by lazy { MovieRepository(apiService, database.movieDao()) }

    override fun newImageLoader(context: android.content.Context): ImageLoader {
        return ImageLoader.Builder(context).components {
                add(OkHttpNetworkFetcherFactory())
            }.build()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
