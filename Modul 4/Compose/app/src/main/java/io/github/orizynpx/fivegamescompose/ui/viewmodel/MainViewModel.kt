package io.github.orizynpx.fivegamescompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import io.github.orizynpx.fivegamescompose.data.GameRepository
import io.github.orizynpx.fivegamescompose.data.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val repository = GameRepository()

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games.asStateFlow()

    init {
        _games.value = repository.getGames()
    }
}