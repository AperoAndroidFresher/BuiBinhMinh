package com.example.buibinhminh.ui.playlist

import com.example.buibinhminh.data.Song

sealed interface PlaylistIntent {
    data object LoadSongs : PlaylistIntent
    data object ToggleViewMode : PlaylistIntent
    data class DeleteSong(val song: Song) : PlaylistIntent
}