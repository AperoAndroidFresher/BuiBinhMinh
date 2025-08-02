package com.example.buibinhminh.ui.playlistSong

import com.example.buibinhminh.data.Song

sealed interface PlaylistIntent {
    data class SetPlaylist(val songs: List<Song>) : PlaylistIntent
    data object ToggleViewMode : PlaylistIntent
    data class DeleteSong(val song: Song) : PlaylistIntent
}