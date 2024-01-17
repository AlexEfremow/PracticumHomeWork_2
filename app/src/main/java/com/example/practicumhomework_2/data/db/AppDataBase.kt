package com.example.practicumhomework_2.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practicumhomework_2.addToPlaylist.data.PlaylistTrackDao
import com.example.practicumhomework_2.addToPlaylist.data.entity.PlaylistTrackDbEntity
import com.example.practicumhomework_2.createPlaylist.data.PlaylistDao
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity
import com.example.practicumhomework_2.data.db.entity.FavoriteTrackDbEntity

@Database(version = 1, entities = [FavoriteTrackDbEntity::class, PlaylistEntity::class, PlaylistTrackDbEntity::class])
abstract class AppDataBase: RoomDatabase() {
    abstract fun favoriteTracksDao(): FavoriteTracksDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun playlistTrackDao(): PlaylistTrackDao
}