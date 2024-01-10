package com.example.practicumhomework_2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practicumhomework_2.data.db.entity.TrackDbEntity

@Database(version = 1, entities = [TrackDbEntity::class])
abstract class AppDataBase: RoomDatabase() {
    abstract fun trackDao(): TrackDao
}