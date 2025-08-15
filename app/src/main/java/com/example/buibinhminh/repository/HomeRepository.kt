package com.example.buibinhminh.repository

import com.example.buibinhminh.data.Album
import com.example.buibinhminh.data.Artist
import com.example.buibinhminh.data.Track
import com.example.buibinhminh.data.toAlbumList
import com.example.buibinhminh.data.toArtistList
import com.example.buibinhminh.data.toTrackList
import com.example.buibinhminh.retrofit.HomeApiClient

class HomeRepository {
    private val apiService = HomeApiClient.build()

    suspend fun getAlbums(): List<Album> {
        val response = apiService.getTopAlbums()
        return if (response.isSuccessful) {
            response.body()?.toAlbumList() ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getTracks(): List<Track> {
        val response = apiService.getTopTracks()
        return if (response.isSuccessful) {
            response.body()?.toTrackList() ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getArtists(): List<Artist> {
        val response = apiService.getTopArtists()
        return if (response.isSuccessful) {
            response.body()?.toArtistList() ?: emptyList()
        } else {
            emptyList()
        }
    }
}