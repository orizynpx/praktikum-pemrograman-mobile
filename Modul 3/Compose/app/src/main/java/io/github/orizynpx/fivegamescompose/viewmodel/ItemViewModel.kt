package io.github.orizynpx.fivegamescompose.viewmodel

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import io.github.orizynpx.fivegamescompose.model.DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        val items = DataSource().loadItems()
        val currentLocale = AppCompatDelegate.getApplicationLocales().get(0)?.language ?: "en"
        _uiState.value = HomeUiState(
            itemList = items,
            currentItemIndex = 0,
            selectedLocale = currentLocale
        )
    }

    fun updateCurrentItem(index: Int) {
        _uiState.value = _uiState.value.copy(
            currentItemIndex = index
        )
    }

    fun updateLocale(locale: String) {
        _uiState.value = _uiState.value.copy(
            selectedLocale = locale,
        )
        val appLocale = LocaleListCompat.forLanguageTags(locale)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}