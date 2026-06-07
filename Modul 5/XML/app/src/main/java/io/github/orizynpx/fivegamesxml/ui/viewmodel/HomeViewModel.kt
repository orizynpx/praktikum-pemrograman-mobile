package io.github.orizynpx.fivegamesxml.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.orizynpx.fivegamesxml.BuildConfig
import io.github.orizynpx.fivegamesxml.data.MovieRepository
import io.github.orizynpx.fivegamesxml.data.local.entity.MovieEntity
import io.github.orizynpx.fivegamesxml.data.remote.NetworkResult
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
        refreshMovies()
    }

    fun refreshMovies() {
        viewModelScope.launch {
            val apiKey = BuildConfig.TMDB_API_KEY
            repository.fetchAndCacheMovies(apiKey).collect { result ->
                _refreshState.value = result
            }
        }
    }

    fun onDetailClicked(movie: MovieEntity) {
        _navigateToDetail.value = movie
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }
}
