package io.github.orizynpx.fivemoviesxml.ui.viewmodel

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
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
    application: Application, private val repository: MovieRepository
) : AndroidViewModel(application) {

    private val _timeInterval = MutableStateFlow("week")
    val timeInterval: StateFlow<String> = _timeInterval.asStateFlow()

    private val _listLimit = MutableStateFlow(5)
    val listLimit: StateFlow<Int> = _listLimit.asStateFlow()

    val carouselMovies: StateFlow<List<MovieEntity>> =
        repository.movies.map { movies -> movies.take(5) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val listMovies: StateFlow<List<MovieEntity>> =
        combine(repository.movies, _listLimit) { movies, limit ->
            movies.take(limit)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _refreshState = MutableStateFlow<NetworkResult<Unit>>(NetworkResult.Loading)
    val refreshState: StateFlow<NetworkResult<Unit>> = _refreshState.asStateFlow()

    private val _navigateToDetail = MutableStateFlow<MovieEntity?>(null)
    val navigateToDetail: StateFlow<MovieEntity?> = _navigateToDetail.asStateFlow()

    init {
        Timber.d("GALAT: HomeViewModel dibuat")
        refreshMovies()
    }

    fun setTimeInterval(interval: String) {
        if (_timeInterval.value != interval) {
            _timeInterval.value = interval
            _listLimit.value = 5
            refreshMovies()
        }
    }

    fun loadMore() {
        if (_listLimit.value < 20) {
            _listLimit.value = (_listLimit.value + 5).coerceAtMost(20)
        }
    }

    fun refreshMovies() {
        viewModelScope.launch {
            val apiKey = BuildConfig.TMDB_API_KEY
            val apiWindow = when (_timeInterval.value) {
                "day" -> "day"
                "week" -> "week"
                else -> "all"
            }

            val appLanguage = AppCompatDelegate.getApplicationLocales()[0]?.language ?: "en"
            val apiLanguage = if (appLanguage == "in") "id-ID" else "en-US"

            repository.fetchTrendingMovies(apiKey, apiWindow, apiLanguage).collect { result ->
                if (result is NetworkResult.Error && !repository.isEmpty()) {
                    Timber.d("GALAT: Network refresh failed but cache exists. Ignoring error for UI.")
                    _refreshState.value = NetworkResult.Success(Unit)
                } else {
                    _refreshState.value = result
                }

                if (result is NetworkResult.Success) {
                    val titles = listMovies.value.joinToString { it.title }
                    Timber.d("GALAT: Item di-load sejumlah ${listMovies.value.size}: $titles")
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
