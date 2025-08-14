package com.example.buibinhminh.ui.playlistSong

import com.example.buibinhminh.data.Song

sealed interface PlaylistIntent {
    data object ToggleViewMode : PlaylistIntent
    data class DeleteSong(val song: Song) : PlaylistIntent
    data object ToggleSortMode : PlaylistIntent
    data class UpdateSongOrder(val fromIndex: Int, val toIndex: Int) : PlaylistIntent
    data object SaveSongOrder : PlaylistIntent
}