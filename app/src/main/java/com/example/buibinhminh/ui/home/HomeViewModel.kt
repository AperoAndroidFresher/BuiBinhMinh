package com.example.buibinhminh.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.data.Album
import com.example.buibinhminh.data.Artist
import com.example.buibinhminh.data.Track
import com.example.buibinhminh.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums.asStateFlow()

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks.asStateFlow()

    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists: StateFlow<List<Artist>> = _artists.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchData()
    }

    internal fun fetchData() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                _albums.value = repository.getAlbums()
                _tracks.value = repository.getTracks()
                _artists.value = repository.getArtists()
            } catch (e: Exception) {
                _error.value = "Failed to load data. Please check your internet connection."
            } finally {
                _isLoading.value = false
            }
        }
    }

}