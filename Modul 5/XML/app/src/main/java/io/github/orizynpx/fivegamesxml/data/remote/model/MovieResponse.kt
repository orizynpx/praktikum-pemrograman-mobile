package io.github.orizynpx.fivegamesxml.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val movies: MovieDTO
)
