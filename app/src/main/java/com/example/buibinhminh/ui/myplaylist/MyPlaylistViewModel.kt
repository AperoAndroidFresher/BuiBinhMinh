package com.example.buibinhminh.ui.myplaylist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.data.Playlist
import com.example.buibinhminh.database.entity.PlaylistEntity
import com.example.buibinhminh.database.entity.toPlaylist
import com.example.buibinhminh.database.relationships.toPlaylist
import com.example.buibinhminh.repository.PlaylistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyPlaylistViewModel(
    private val playlistRepository: PlaylistRepository,
    private val userId: Int
) : ViewModel() {
    private val _state = MutableStateFlow(MyPlaylistState())
    val state: StateFlow<MyPlaylistState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            playlistRepository.getPlaylistsWithSongsForUser(userId)
                .map { playlistSongsList ->
                    playlistSongsList.map { it.toPlaylist() }
                }
                .collect { updatedPlaylists ->
                    _state.update { it.copy(playlists = updatedPlaylists) }
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
                     viewModelScope.launch {
                         playlistRepository.updatePlaylistName(playlistIdToRename, newName)
                     }
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
                    viewModelScope.launch {
                        val newPlaylistEntity = PlaylistEntity(name = name, userId = userId)
                        val playlistId = playlistRepository.insertPlaylist(newPlaylistEntity)
                        _state.update {
                            it.copy(isCreatingPlaylist = false, newPlaylistName = "")
                        }
                    }
                }
            }

            is MyPlaylistIntent.OnDeletePlaylist -> {
                viewModelScope.launch {
                    playlistRepository.deletePlaylist(intent.playlistId)
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