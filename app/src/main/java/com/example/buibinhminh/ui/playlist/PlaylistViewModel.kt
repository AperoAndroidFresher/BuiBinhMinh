package com.example.buibinhminh.ui.playlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.data.Song
import com.example.buibinhminh.helper.getAllMp3Files
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(PlaylistState())
    val state: StateFlow<PlaylistState> = _state.asStateFlow()

    fun processIntent(intent: PlaylistIntent) {
        when (intent) {
            PlaylistIntent.LoadSongs -> loadSongs()
            PlaylistIntent.ToggleViewMode -> toggleViewMode()
            is PlaylistIntent.DeleteSong -> deleteSong(intent.song)
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

    private fun toggleViewMode() {
        Log.d("MVI_DEBUG", "Processing Intent: ToggleViewMode")
        _state.update { it.copy(isGridView = !it.isGridView) }
    }

    private fun deleteSong(song: Song) {
        Log.d("MVI_DEBUG", "Processing Intent: DeleteSong - ${song.title}")
        _state.update { currentState ->
            val updatedSongs = currentState.songs.toMutableList()
            updatedSongs.remove(song)
            currentState.copy(songs = updatedSongs)
        }
    }
}