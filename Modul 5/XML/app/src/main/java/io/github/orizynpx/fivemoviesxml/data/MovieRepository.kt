package io.github.orizynpx.fivemoviesxml.data

import io.github.orizynpx.fivemoviesxml.data.local.dao.MovieDao
import io.github.orizynpx.fivemoviesxml.data.local.entity.MovieEntity
import io.github.orizynpx.fivemoviesxml.data.remote.ApiService
import io.github.orizynpx.fivemoviesxml.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class MovieRepository(
    private val apiService: ApiService, private val movieDao: MovieDao
) {
    val movies: Flow<List<MovieEntity>> = movieDao.getAllMovies()

    suspend fun isEmpty(): Boolean = movies.first().isEmpty()

    suspend fun fetchTrendingMovies(
        apiKey: String, timeWindow: String, language: String = "en-US"
    ): Flow<NetworkResult<Unit>> = flow {
        emit(NetworkResult.Loading)
        try {
            val responseEn = if (timeWindow == "all") {
                apiService.getPopularMovies(apiKey, "en-US")
            } else {
                apiService.getTrendingMovies(timeWindow, apiKey, "en-US")
            }

            if (responseEn.isSuccessful) {
                val movieDtosEn = responseEn.body()?.movies ?: emptyList()

                val finalMovieEntities = if (language == "en-US") {
                    movieDtosEn.map { dto ->
                        MovieEntity(
                            id = dto.id,
                            title = dto.title,
                            overview = dto.overview,
                            posterPath = dto.posterPath,
                            backdropPath = dto.backdropPath,
                            releaseDate = dto.releaseDate
                        )
                    }
                } else {
                    val responseLocalized = if (timeWindow == "all") {
                        apiService.getPopularMovies(apiKey, language)
                    } else {
                        apiService.getTrendingMovies(timeWindow, apiKey, language)
                    }

                    val movieDtosLocalized = if (responseLocalized.isSuccessful) {
                        responseLocalized.body()?.movies ?: emptyList()
                    } else {
                        emptyList()
                    }

                    movieDtosEn.map { dtoEn ->
                        val localizedDto = movieDtosLocalized.find { it.id == dtoEn.id }
                        val localizedOverview = localizedDto?.overview

                        MovieEntity(
                            id = dtoEn.id,
                            title = dtoEn.title,
                            overview = if (!localizedOverview.isNullOrBlank()) localizedOverview else dtoEn.overview,
                            posterPath = dtoEn.posterPath,
                            backdropPath = dtoEn.backdropPath,
                            releaseDate = dtoEn.releaseDate
                        )
                    }
                }

                if (finalMovieEntities.isNotEmpty()) {
                    movieDao.clearMovies()
                    movieDao.insertMovies(finalMovieEntities)
                }
                emit(NetworkResult.Success(Unit))
            } else {
                emit(NetworkResult.Error("API Error: ${responseEn.code()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching trending movies")
            emit(NetworkResult.Error("Network Error: ${e.message}", e))
        }
    }

    suspend fun fetchAndCacheMovies(apiKey: String): Flow<NetworkResult<Unit>> = flow {
        fetchTrendingMovies(apiKey, "week").collect { emit(it) }
    }
}
