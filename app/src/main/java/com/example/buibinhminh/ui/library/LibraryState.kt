package com.example.buibinhminh.ui.library

import com.example.buibinhminh.data.Song


data class LibraryState(
    val songs: List<Song> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showAddToPlaylistDialog: Boolean = false,
    val selectedSongForPlaylist: Song? = null
) {
    val isEmpty: Boolean
        get() = songs.isEmpty()
}