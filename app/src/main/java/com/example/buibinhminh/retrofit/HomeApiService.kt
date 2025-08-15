package com.example.buibinhminh.retrofit

import com.example.buibinhminh.data.TopAlbumsResponse
import com.example.buibinhminh.data.TopArtistsResponse
import com.example.buibinhminh.data.TopTracksResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApiService {
    @GET("/2.0/?api_key=e65449d181214f936368984d4f4d4ae8&format=json&method=artist.getTopAlbums&mbid=f9b593e6-4503-414c-99a0-46595ecd2e23")
    suspend fun getTopAlbums(): Response<TopAlbumsResponse>


    @GET("/2.0/?api_key=e65449d181214f936368984d4f4d4ae8&format=json&method=artist.getTopTracks&mbid=f9b593e6-4503-414c-99a0-46595ecd2e23")
    suspend fun getTopTracks(): Response<TopTracksResponse>

    @GET("/2.0/?api_key=e65449d181214f936368984d4f4d4ae8&format=json&method=chart.gettopartists")
    suspend fun getTopArtists(): Response<TopArtistsResponse>
}