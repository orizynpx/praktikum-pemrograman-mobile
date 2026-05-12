package io.github.orizynpx.fivegamesxml.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigationevent.NavigationEvent
import io.github.orizynpx.fivegamesxml.data.GameRepository
import io.github.orizynpx.fivegamesxml.data.model.Game
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class MainViewModel(private val locale: String) : ViewModel() {
    private val repository = GameRepository()

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games.asStateFlow()

    init {
        _games.value = repository.getGames()
        Timber.d("MainViewModel dibuat dengan label $locale")
    }
}