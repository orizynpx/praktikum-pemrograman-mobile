package io.github.orizynpx.fivegamescompose.viewmodel

import io.github.orizynpx.fivegamescompose.model.ItemModel

data class HomeUiState(
    val itemList: List<ItemModel> = emptyList(),
    val currentItemIndex: Int = 0,
    val selectedLocale: String = "en"
)
