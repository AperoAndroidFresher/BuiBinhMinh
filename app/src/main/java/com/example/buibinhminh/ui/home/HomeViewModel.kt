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

    init {
        fetchTopAlbums()
        fetchTopTracks()
        fetchTopArtists()
    }

    private fun fetchTopAlbums() {
        viewModelScope.launch {
            val fetchedAlbums = repository.getAlbums()
            _albums.value = fetchedAlbums
        }
    }

    private fun fetchTopTracks() {
        viewModelScope.launch {
            val fetchedTracks = repository.getTracks()
            _tracks.value = fetchedTracks
        }
    }

    private fun fetchTopArtists() {
        viewModelScope.launch {
            val fetchedArtists = repository.getArtists()
            _artists.value = fetchedArtists
        }
    }
}