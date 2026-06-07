package io.github.orizynpx.fivegamesxml.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val page: Int,
    @SerialName("results")
    val movies: List<MovieDto>
)
