package com.example.buibinhminh.ui.playlistSong

import com.example.buibinhminh.data.Song

data class PlaylistState(
    val songs: List<Song> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isGridView: Boolean = false
) {
    val isEmpty: Boolean
        get() = songs.isEmpty()
}
