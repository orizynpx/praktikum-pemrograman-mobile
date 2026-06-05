package io.github.orizynpx.fivegamesxml.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Game(
    val id: Int,
    @DrawableRes val imageResourceId: Int,
    @StringRes val titleResourceId: Int,
    @StringRes val yearResourceId: Int,
    @StringRes val genreResourceId: Int,
    @StringRes val detailResourceId: Int,
    @StringRes val url: Int
)