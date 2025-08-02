package com.example.buibinhminh.ui.myplaylist

import com.example.buibinhminh.data.Playlist

sealed interface MyPlaylistIntent {
    data object OnAddClick : MyPlaylistIntent
    data class OnPlaylistNameChange(val name: String) : MyPlaylistIntent
    data object OnCreateConfirm : MyPlaylistIntent
    data object OnCancelCreate : MyPlaylistIntent
    data class OnDeletePlaylist(val playlistId: Long) : MyPlaylistIntent
    data class OnRenameClick(val playlist: Playlist) : MyPlaylistIntent
    data object OnCancelRename : MyPlaylistIntent
    data object OnRenamePlaylistConfirm :MyPlaylistIntent
    data class OnPlaylistClick(val playlist: Playlist) : MyPlaylistIntent
}