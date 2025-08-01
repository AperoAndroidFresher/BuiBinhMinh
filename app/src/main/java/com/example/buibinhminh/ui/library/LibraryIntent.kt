package com.example.buibinhminh.ui.library

import com.example.buibinhminh.data.Song

sealed interface LibraryIntent {
    data object LoadSongs : LibraryIntent
    data class AddToPlaylist(val song: Song, val playlistId: Long) : LibraryIntent
    data object HideAddToPlaylistDialog : LibraryIntent
    data class ShowAddToPlaylistDialog(val song: Song) : LibraryIntent
}