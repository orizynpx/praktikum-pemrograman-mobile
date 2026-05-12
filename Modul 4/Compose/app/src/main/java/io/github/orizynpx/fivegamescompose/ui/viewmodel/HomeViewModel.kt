package io.github.orizynpx.fivegamescompose.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.github.orizynpx.fivegamescompose.data.GameRepository
import io.github.orizynpx.fivegamescompose.data.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class HomeViewModel(application: Application, private val appLabel: String) : AndroidViewModel(application) {
    private val repository = GameRepository()

    private val _gameList = MutableStateFlow<List<Game>>(emptyList())
    val gameList: StateFlow<List<Game>> = _gameList.asStateFlow()

    private val _navigateToDetail = MutableStateFlow<Game?>(null)
    val navigateToDetail: StateFlow<Game?> = _navigateToDetail.asStateFlow()

    private val _navigateToLink = MutableStateFlow<String?>(null)
    val navigateToLink: StateFlow<String?> = _navigateToLink.asStateFlow()

    init {
        Timber.d("GALAT: HomeViewModel dibuat dengan label $appLabel")
        loadGames(repository.getGames())
    }

    fun loadGames(games: List<Game>) {
        _gameList.value = games
        val gameTitles = games.map { getApplication<Application>().getString(it.titleResourceId) }
        Timber.d("GALAT: Item di-load sejumlah ${_gameList.value.size}: ${gameTitles}")
    }

    fun onDetailClicked(game: Game) {
        Timber.d("GALAT: Tombol Detail ditekan")
        _navigateToDetail.value = game
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    fun onLinkClicked(url: String) {
        Timber.d("GALAT: Tombol explicit intent ditekan")
        _navigateToLink.value = url
    }

    fun onLinkNavigated() {
        _navigateToLink.value = null
    }
}