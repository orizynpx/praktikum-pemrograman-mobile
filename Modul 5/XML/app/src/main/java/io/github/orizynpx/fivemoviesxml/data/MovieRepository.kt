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
    private val apiService: ApiService,
    private val movieDao: MovieDao
) {
    val movies: Flow<List<MovieEntity>> = movieDao.getAllMovies()

    suspend fun isEmpty(): Boolean = movies.first().isEmpty()

    suspend fun fetchTrendingMovies(apiKey: String, timeWindow: String): Flow<NetworkResult<Unit>> = flow {
        emit(NetworkResult.Loading)
        try {
            val response = if (timeWindow == "all") {
                apiService.getPopularMovies(apiKey)
            } else {
                apiService.getTrendingMovies(timeWindow, apiKey)
            }
            
            if (response.isSuccessful) {
                val movieDtos = response.body()?.movies ?: emptyList()
                val movieEntities = movieDtos.map { dto ->
                    MovieEntity(
                        id = dto.id,
                        title = dto.title,
                        overview = dto.overview,
                        posterPath = dto.posterPath,
                        backdropPath = dto.backdropPath,
                        releaseDate = dto.releaseDate
                    )
                }
                
                if (movieEntities.isNotEmpty()) {
                    movieDao.clearMovies()
                    movieDao.insertMovies(movieEntities)
                }
                emit(NetworkResult.Success(Unit))
            } else {
                emit(NetworkResult.Error("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching trending movies")
            emit(NetworkResult.Error("Network Error: ${e.message}", e))
        }
    }

    suspend fun fetchAndCacheMovies(apiKey: String): Flow<NetworkResult<Unit>> = flow {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.getPopularMovies(apiKey)
            if (response.isSuccessful) {
                val movieDtos = response.body()?.movies ?: emptyList()
                val movieEntities = movieDtos.map { dto ->
                    MovieEntity(
                        id = dto.id,
                        title = dto.title,
                        overview = dto.overview,
                        posterPath = dto.posterPath,
                        backdropPath = dto.backdropPath,
                        releaseDate = dto.releaseDate
                    )
                }
                
                if (movieEntities.isNotEmpty()) {
                    movieDao.clearMovies()
                    movieDao.insertMovies(movieEntities)
                }
                emit(NetworkResult.Success(Unit))
            } else {
                emit(NetworkResult.Error("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching movies")
            emit(NetworkResult.Error("Network Error: ${e.message}", e))
        }
    }
}
