package com.example.tubechallenge.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="stop-table")
data class StopEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name="station-name")
    val stationName: String= "",
    @ColumnInfo(name="time-arrived")
    val timeArrived: String= "",
    @ColumnInfo(name="time-departed")
    val timeDeparted: String= "",
    @ColumnInfo(name="notes")
    val notes: String= ""
)