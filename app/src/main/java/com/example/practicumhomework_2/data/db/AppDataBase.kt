package com.example.practicumhomework_2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practicumhomework_2.createPlaylist.data.PlaylistDao
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity
import com.example.practicumhomework_2.data.db.entity.TrackDbEntity

@Database(version = 1, entities = [TrackDbEntity::class, PlaylistEntity::class])
abstract class AppDataBase: RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao
}