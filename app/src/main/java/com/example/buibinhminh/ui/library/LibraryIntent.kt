package com.example.buibinhminh.ui.library

import com.example.buibinhminh.data.Song

sealed interface LibraryIntent {
    data object LoadLocalSongs : LibraryIntent
    data object LoadRemoteSongs : LibraryIntent
    data class AddToPlaylist(val song: Song, val playlistId: Long) : LibraryIntent
    data object NavigateToCreatePlaylist : LibraryIntent
    data object HideAddToPlaylistDialog : LibraryIntent
    data class ShowAddToPlaylistDialog(val song: Song) : LibraryIntent
}