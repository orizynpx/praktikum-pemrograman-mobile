package io.github.orizynpx.fivegamesxml.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.orizynpx.fivegamesxml.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    //    @Query(value = <statement>) fun query(): Flow<List<MovieEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert()

//    @Query
    suspend fun delete()
}