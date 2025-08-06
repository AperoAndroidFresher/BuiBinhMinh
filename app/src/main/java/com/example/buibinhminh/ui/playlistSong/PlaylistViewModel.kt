package com.example.buibinhminh.ui.playlistSong

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.data.Playlist
import com.example.buibinhminh.data.Song
import com.example.buibinhminh.database.entity.toSong
import com.example.buibinhminh.repository.PlaylistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistRepository: PlaylistRepository,
    private val playlistId: Long
) : ViewModel(){
    private val _state = MutableStateFlow(PlaylistState())
    val state: StateFlow<PlaylistState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            playlistRepository.getPlaylistWithSongs(playlistId)
                .collect { playlistSongs ->
                    if (playlistSongs != null && playlistSongs.playlist != null) {
                        val songs = playlistSongs.songs.map { it.toSong() }
                        _state.update {
                            it.copy(
                                songs = songs,
                                isLoading = false,
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                songs = emptyList(),
                                isLoading = false,
                            )
                        }
                    }
                }
        }
    }

    fun processIntent(intent: PlaylistIntent) {
        when (intent) {
            PlaylistIntent.ToggleViewMode -> toggleViewMode()
            is PlaylistIntent.DeleteSong -> {
                viewModelScope.launch {
                    playlistRepository.removeSongFromPlaylist(playlistId, intent.song.id)
                }
            }
        }
    }

    private fun toggleViewMode() {
        Log.d("MVI_DEBUG", "Processing Intent: ToggleViewMode")
        _state.update { it.copy(isGridView = !it.isGridView) }
    }

}