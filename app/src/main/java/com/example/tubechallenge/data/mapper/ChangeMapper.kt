package com.example.tubechallenge.data.mapper

import com.example.tubechallenge.data.model.StopEntity
import com.example.tubechallenge.domain.model.Stop
import javax.inject.Inject

class StopMapper @Inject constructor() {

    fun fromEntityToDomain(stopEntity: StopEntity): Stop {
        return Stop(
            id = stopEntity.id,
            stationName = stopEntity.stationName,
            timeArrived = stopEntity.timeArrived,
            timeDeparted = stopEntity.timeDeparted,
            notes = stopEntity.notes,
        )
    }

    fun fromDomainToEntity(stop: Stop): StopEntity {
        return StopEntity(
            id = stop.id,
            stationName = stop.stationName,
            timeArrived = stop.timeArrived,
            timeDeparted = stop.timeDeparted,
            notes = stop.notes,
        )
    }

    fun fromEntityListToDomainList(stopEntities: List<StopEntity>): List<Stop> {
        return stopEntities.map { fromEntityToDomain(it) }
    }

    fun fromDomainListToEntityList(stops: List<Stop>): List<StopEntity> {
        return stops.map { fromDomainToEntity(it) }
    }
}
