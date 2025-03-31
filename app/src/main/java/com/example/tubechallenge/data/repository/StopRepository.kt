package com.example.tubechallenge.data.repository

import com.example.tubechallenge.data.db.StopDao
import com.example.tubechallenge.data.mapper.StopMapper
import com.example.tubechallenge.data.model.StopEntity
import com.example.tubechallenge.domain.model.Stop
import com.example.tubechallenge.domain.repository.StopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StopRepositoryImpl @Inject constructor(
    private val stopDao: StopDao,
    private val stopMapper: StopMapper
): StopRepository {

    override suspend fun addStop(stop: Stop){
        val stopEntity = stopMapper.fromDomainToEntity(stop)
        stopDao.addStop(stopEntity)
    }

    override fun getAllStops(): Flow<List<Stop>> {
        return stopDao.getAllStops().map { stopEntities: List<StopEntity> ->
            stopMapper.fromEntityListToDomainList(stopEntities)
        }
    }

    override fun getStopById(id: Long): Flow<Stop> {
        return stopDao.getStopById(id).map { stopEntity: StopEntity->
            stopMapper.fromEntityToDomain(stopEntity)
        }
    }

    override suspend fun updateStop(stop: Stop) {
        val stopEntity = stopMapper.fromDomainToEntity(stop)
        stopDao.updateStop(stopEntity)
    }

    override suspend fun deleteStop(stop: Stop) {
        val stopEntity = stopMapper.fromDomainToEntity(stop)
        stopDao.deleteStop(stopEntity)
    }
}