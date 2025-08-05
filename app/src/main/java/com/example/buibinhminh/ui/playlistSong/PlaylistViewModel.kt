package com.example.buibinhminh.ui.playlistSong

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.buibinhminh.data.Playlist
import com.example.buibinhminh.data.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlaylistViewModel(
    private val sharedPlaylists: MutableState<List<Playlist>>,
    private val playlistId: Long
) : ViewModel(){
    private val _state = MutableStateFlow(PlaylistState())
    val state: StateFlow<PlaylistState> = _state.asStateFlow()

    init {
        val songs = sharedPlaylists.value
            .firstOrNull { it.id == playlistId }
            ?.songs.orEmpty()
        _state.value = PlaylistState(songs = songs)
    }

    fun processIntent(intent: PlaylistIntent) {
        when (intent) {
            is PlaylistIntent.SetPlaylist -> setPlaylist(intent.songs)
            PlaylistIntent.ToggleViewMode -> toggleViewMode()
            is PlaylistIntent.DeleteSong -> {
                _state.update { st ->
                    st.copy(songs = st.songs.filterNot { it.id == intent.song.id })
                }
                sharedPlaylists.value = sharedPlaylists.value.map { pl ->
                    if (pl.id == playlistId) {
                        pl.copy(songs = pl.songs.filterNot { it.id == intent.song.id })
                    } else pl
                }
            }
        }
    }

    private fun setPlaylist(songs: List<Song>) {
        _state.update {
            it.copy(
                songs = songs,
                isLoading = false
            )
        }
    }

    private fun toggleViewMode() {
        Log.d("MVI_DEBUG", "Processing Intent: ToggleViewMode")
        _state.update { it.copy(isGridView = !it.isGridView) }
    }

}