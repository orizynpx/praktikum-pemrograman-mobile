package io.github.orizynpx.fivemoviesxml.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.orizynpx.fivemoviesxml.BuildConfig
import io.github.orizynpx.fivemoviesxml.data.MovieRepository
import io.github.orizynpx.fivemoviesxml.data.local.entity.MovieEntity
import io.github.orizynpx.fivemoviesxml.data.remote.NetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    application: Application,
    private val repository: MovieRepository
) : AndroidViewModel(application) {

    val movieList: StateFlow<List<MovieEntity>> = repository.movies
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _refreshState = MutableStateFlow<NetworkResult<Unit>>(NetworkResult.Loading)
    val refreshState: StateFlow<NetworkResult<Unit>> = _refreshState.asStateFlow()

    private val _navigateToDetail = MutableStateFlow<MovieEntity?>(null)
    val navigateToDetail: StateFlow<MovieEntity?> = _navigateToDetail.asStateFlow()

    init {
        Timber.d("GALAT: HomeViewModel dibuat")
        refreshMovies()
    }

    fun refreshMovies() {
        viewModelScope.launch {
            val apiKey = BuildConfig.TMDB_API_KEY
            repository.fetchAndCacheMovies(apiKey).collect { result ->
                // Offline-first behavior: Only report errors if the database is currently empty
                if (result is NetworkResult.Error && !repository.isEmpty()) {
                    Timber.d("GALAT: Network refresh failed but cache exists. Ignoring error for UI.")
                    _refreshState.value = NetworkResult.Success(Unit) 
                } else {
                    _refreshState.value = result
                }

                if (result is NetworkResult.Success) {
                    val titles = movieList.value.joinToString { it.title }
                    Timber.d("GALAT: Item di-load sejumlah ${movieList.value.size}: $titles")
                }
            }
        }
    }

    fun onDetailClicked(movie: MovieEntity) {
        Timber.d("GALAT: Tombol Detail ditekan")
        _navigateToDetail.value = movie
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }
}
