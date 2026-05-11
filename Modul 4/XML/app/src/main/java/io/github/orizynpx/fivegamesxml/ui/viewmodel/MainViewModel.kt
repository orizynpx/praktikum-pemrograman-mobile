package io.github.orizynpx.fivegamesxml.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.orizynpx.fivegamesxml.data.GameRepository
import io.github.orizynpx.fivegamesxml.data.model.Game

class MainViewModel : ViewModel() {
    private val repository = GameRepository()
    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> = _games

    init {
        _games.value = repository.getGames()
    }
}