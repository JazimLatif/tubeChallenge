package com.example.tubechallenge.domain.repository

import com.example.tubechallenge.domain.model.Stop
import kotlinx.coroutines.flow.Flow

interface StopRepository {
    suspend fun addStop(stop: Stop)
    fun getAllStops(): Flow<List<Stop>>
    fun getStopById(id: Long): Flow<Stop>
    suspend fun updateStop(stop: Stop)
    suspend fun deleteStop(stop: Stop)
}