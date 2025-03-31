package com.example.tubechallenge.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tubechallenge.data.model.StopEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class StopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addStop(stopEntity: StopEntity)

    // Loads all stops from stop-table
    @Query("SELECT * FROM `stop-table`")
    abstract fun getAllStops(): Flow<List<StopEntity>>

    // Loads a stop from stop-table based on ID
    @Query("SELECT * FROM `stop-table` WHERE id=:id")
    abstract fun getStopById(id: Long): Flow<StopEntity>

    @Update
    abstract suspend fun updateStop(stopEntity: StopEntity)

    @Delete
    abstract suspend fun deleteStop(stopEntity: StopEntity)

}