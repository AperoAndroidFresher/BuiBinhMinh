package com.example.buibinhminh.ui.myplaylist

import com.example.buibinhminh.data.Playlist

data class MyPlaylistState(
    val playlists: List<Playlist> = emptyList(),
    val isCreatingPlaylist: Boolean = false,
    val newPlaylistName: String = "",
    val selectedPlaylist: Playlist? = null,
    val isRenamingPlaylist: Boolean = false,
    val renamePlaylistName: String = "",
    val renamePlaylistId: Long? = null
)