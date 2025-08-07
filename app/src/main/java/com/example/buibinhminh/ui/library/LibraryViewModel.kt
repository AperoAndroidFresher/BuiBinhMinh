package com.example.buibinhminh.ui.library

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.data.Playlist
import com.example.buibinhminh.data.SongDto
import com.example.buibinhminh.data.toSong
import com.example.buibinhminh.data.toSongEntity
import com.example.buibinhminh.database.relationships.toPlaylist
import com.example.buibinhminh.helper.getAllMp3Files
import com.example.buibinhminh.repository.PlaylistRepository
import com.example.buibinhminh.retrofit.ApiClient
import com.example.buibinhminh.storage.saveFileToInternalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryViewModel(
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
            LibraryIntent.LoadLocalSongs -> loadLocalSongs()
            LibraryIntent.LoadRemoteSongs -> viewModelScope.launch { loadRemoteSongs()}
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

    private fun loadLocalSongs() {
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
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load songs: ${e.message}"
                    )
                }
            }
        }
    }

    private suspend fun loadRemoteSongs() {
        _state.update { it.copy(isLoading = true, error = null) }

        try {
            val response = ApiClient.build().getSongs()

            if (response.isSuccessful) {
                val songDtos = response.body() ?: emptyList()
                val downloadedSongs = songDtos.map { songDto ->
                    val song = songDto.toSong()
                    val songFileResponse = ApiClient.build().downloadSong(song.contentUri.toString())
                    val savedUri =
                        saveFileToInternalStorage(getApplication(), song, songFileResponse)
                    song.copy(contentUri = savedUri ?: song.contentUri)
                }

                _state.update {
                    it.copy(
                        songs = downloadedSongs,
                        isLoading = false
                    )
                }
            } else {
                _state.update {
                    it.copy(isLoading = false, error = "API failed with code: ${response.code()}")
                }
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(isLoading = false, error = "Network request failed: ${e.message}")
            }
        }
    }

    private fun navigateToCreatePlaylist() {

    }
}
