package io.github.orizynpx.fivegamesxml.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.orizynpx.fivegamesxml.data.local.entity.MovieEntity

//@Database(entities = MovieEntity, version = 0)
abstract class AppDatabase : RoomDatabase() {
    abstract fun returnMovieDAO()
    companion object MovieEntity
}