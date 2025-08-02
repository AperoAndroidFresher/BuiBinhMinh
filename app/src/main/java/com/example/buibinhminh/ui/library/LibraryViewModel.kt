package com.example.buibinhminh.ui.library

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.buibinhminh.data.Playlist
import com.example.buibinhminh.helper.getAllMp3Files
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel (
    application: Application,
    private val sharedPlaylists: MutableState<List<Playlist>>
) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(LibraryState())
    val state: StateFlow<LibraryState> = _state.asStateFlow()
    val playlists: List<Playlist> get() = sharedPlaylists.value

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
            is LibraryIntent.NavigateToCreatePlaylist -> navigateToCreatePlaylist()
            is LibraryIntent.AddToPlaylist -> {
                val updated = sharedPlaylists.value.map { playlist ->
                    if (playlist.id == intent.playlistId) {
                        if (!playlist.songs.contains(intent.song)) {
                            playlist.copy(songs = playlist.songs + intent.song)
                        } else playlist
                    } else playlist
                }
                sharedPlaylists.value = updated
                processIntent(LibraryIntent.HideAddToPlaylistDialog)
            }
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

    private fun navigateToCreatePlaylist(){

    }
}

class LibraryViewModelFactory(
    private val application: Application,
    private val sharedPlaylists: MutableState<List<Playlist>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LibraryViewModel(application, sharedPlaylists) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun libraryViewModel(
    sharedPlaylists: MutableState<List<Playlist>>
): LibraryViewModel {
    val application = LocalContext.current.applicationContext as Application
    val factory = remember(application, sharedPlaylists) {
        LibraryViewModelFactory(application, sharedPlaylists)
    }
    return viewModel(factory = factory)
}