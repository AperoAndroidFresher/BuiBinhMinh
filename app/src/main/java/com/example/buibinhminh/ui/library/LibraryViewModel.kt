package com.example.buibinhminh.ui.library

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.data.Playlist
import com.example.buibinhminh.data.Song
import com.example.buibinhminh.helper.getAllMp3Files
import com.example.buibinhminh.ui.playlist.PlaylistIntent
import com.example.buibinhminh.ui.playlist.PlaylistState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel (application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(LibraryState())
    val state: StateFlow<LibraryState> = _state.asStateFlow()

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()

    init {
        loadPlaylists()
    }

    fun processIntent(intent: LibraryIntent) {
        when (intent) {
            LibraryIntent.LoadSongs -> loadSongs()
            is LibraryIntent.ShowAddToPlaylistDialog -> _state.update {
                it.copy(
                    showAddToPlaylistDialog = true,
                    selectedSongForPlaylist = intent.song
                )
            }
            LibraryIntent.HideAddToPlaylistDialog -> _state.update {
                it.copy(
                    showAddToPlaylistDialog = false,
                    selectedSongForPlaylist = null
                )
            }
            is LibraryIntent.AddToPlaylist -> addToPlaylist(intent.song, intent.playlistId)
        }
    }

    private fun loadSongs() {
        if (_state.value.isLoading) return
        Log.d("MVI_DEBUG", "Processing Intent: LoadSongs")

        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val songs = getAllMp3Files(getApplication())
                _state.update { it.copy(songs = songs, isLoading = false) }
                Log.d("MVI_DEBUG", "Songs loaded. Count: ${songs.size}")
            } catch (e: Exception) {
                Log.e("MVI_DEBUG", "Error loading songs: ${e.message}", e)
                _state.update { it.copy(isLoading = false, error = "Failed to load songs: ${e.message}") } // Update state with error
            }
        }
    }

    private fun loadPlaylists() {
//        _playlists.value = emptyList<Playlist>()
        _playlists.value = listOf(
            Playlist(
                id = 1,
                name = "Test Music",
                songs = emptyList()
            )
        )
    }

    private fun addToPlaylist(song: Song, playlistId: Long) {
        Log.d("MVI_DEBUG", "Processing Intent: AddToPlaylist - ${song.title}")
        processIntent(LibraryIntent.HideAddToPlaylistDialog)
    }
}