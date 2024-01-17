package com.example.practicumhomework_2.addToPlaylist.presentation

sealed class PlaylistState {
    class Success(val playlistName: String): PlaylistState()
    class Unavailable(val playlistName: String): PlaylistState()
}