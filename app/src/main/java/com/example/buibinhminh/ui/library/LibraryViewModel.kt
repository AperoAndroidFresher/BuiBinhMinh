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
import com.example.buibinhminh.data.toSongEntity
import com.example.buibinhminh.database.entity.toPlaylist
import com.example.buibinhminh.database.relationships.toPlaylist
import com.example.buibinhminh.helper.getAllMp3Files
import com.example.buibinhminh.repository.PlaylistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel (
    application: Application,
    private val playlistRepository: PlaylistRepository,
    private val userId: Int
) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(LibraryState())
    val state: StateFlow<LibraryState> = _state.asStateFlow()

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()

    init {
        viewModelScope.launch {
            playlistRepository.getPlaylistsWithSongsForUser(userId)
                .map { playlistSongsList ->
                    playlistSongsList.map { it.toPlaylist() }
                }
                .collect { updatedPlaylists ->
                    _playlists.value = updatedPlaylists
                }
        }
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
            is LibraryIntent.NavigateToCreatePlaylist -> navigateToCreatePlaylist()
            is LibraryIntent.AddToPlaylist -> {
                viewModelScope.launch {
                    val songEntity = intent.song.toSongEntity()

                    playlistRepository.saveSong(songEntity)

                    playlistRepository.addSongToPlaylist(
                        playlistId = intent.playlistId,
                        songId = songEntity.id
                    )
                    processIntent(LibraryIntent.HideAddToPlaylistDialog)
                }
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
    private val playlistRepository: PlaylistRepository,
    private val userId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LibraryViewModel(application, playlistRepository, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun libraryViewModel(
    playlistRepository: PlaylistRepository,
    userId: Int
): LibraryViewModel {
    val application = LocalContext.current.applicationContext as Application
    val factory = remember(application, playlistRepository, userId) {
        LibraryViewModelFactory(application, playlistRepository, userId)
    }
    return viewModel(factory = factory)
}