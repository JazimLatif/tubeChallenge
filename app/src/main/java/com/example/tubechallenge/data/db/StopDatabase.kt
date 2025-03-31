package com.example.tubechallenge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tubechallenge.data.model.StopEntity


@Database(
    entities = [StopEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StopDatabase : RoomDatabase() {
    abstract fun stopDao(): StopDao
}
