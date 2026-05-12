package io.github.orizynpx.fivegamesxml.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModelFactory(private val appLabel: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(appLabel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}