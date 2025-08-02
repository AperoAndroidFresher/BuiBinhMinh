package com.example.buibinhminh.ui.myplaylist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.data.Playlist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyPlaylistViewModel(
    private val sharedPlaylists: MutableState<List<Playlist>>
) : ViewModel() {
    private val _state = MutableStateFlow(MyPlaylistState())
    val state: StateFlow<MyPlaylistState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            // snapshotFlow giúp biến Compose State thành một Flow
            snapshotFlow { sharedPlaylists.value }
                .collect { updatedList ->
                    // Mỗi khi sharedPlaylists thay đổi, cập nhật lại state của UI
                    _state.update { it.copy(playlists = updatedList) }
                }
        }
    }

    fun processIntent(intent: MyPlaylistIntent) {
        when (intent) {
            is MyPlaylistIntent.OnAddClick -> {
                _state.update { it.copy(isCreatingPlaylist = true) }
            }

            is MyPlaylistIntent.OnPlaylistNameChange -> {
                _state.update { it.copy(newPlaylistName = intent.name) }
            }

            is MyPlaylistIntent.OnCancelCreate -> {
                _state.update {
                    it.copy(isCreatingPlaylist = false, newPlaylistName = "")
                }
            }

            is MyPlaylistIntent.OnRenameClick -> {
                _state.update {
                    it.copy(
                        isRenamingPlaylist = true,
                        renamePlaylistId = intent.playlist.id,
                        newPlaylistName = intent.playlist.name
                    )
                }
            }

            MyPlaylistIntent.OnCancelRename -> {
                _state.update {
                    it.copy(
                        isRenamingPlaylist = false,
                        renamePlaylistId = null,
                        newPlaylistName = ""
                    )
                }
            }

            is MyPlaylistIntent.OnRenamePlaylistConfirm -> {
                val newName = _state.value.newPlaylistName.trim()
                val playlistIdToRename = _state.value.renamePlaylistId

                 if (newName.isNotBlank() && playlistIdToRename != null) {
                    val updatedPlaylists = sharedPlaylists.value.map { playlist ->
                        if (playlist.id == playlistIdToRename) {
                            playlist.copy(name = newName)
                        } else playlist
                    }
                    sharedPlaylists.value = updatedPlaylists
                }

               _state.update {
                    it.copy(
                        isRenamingPlaylist = false,
                        renamePlaylistId = null,
                        newPlaylistName = ""
                    )
                }
            }

            is MyPlaylistIntent.OnCreateConfirm -> {
                val name = _state.value.newPlaylistName.trim()
                if (name.isNotBlank()) {
                    val newPlaylist = Playlist(
                        id = System.currentTimeMillis(),
                        name = name
                    )
                    sharedPlaylists.value = sharedPlaylists.value + newPlaylist
                    _state.update {
                        it.copy(
                            isCreatingPlaylist = false,
                            newPlaylistName = ""
                        )
                    }
                }
            }

            is MyPlaylistIntent.OnDeletePlaylist -> {
                sharedPlaylists.value = sharedPlaylists.value.filterNot {
                    it.id == intent.playlistId
                }
            }

            is MyPlaylistIntent.OnPlaylistClick -> {
                _state.update {
                    it.copy(selectedPlaylist = intent.playlist)
                }
            }
        }
    }
}