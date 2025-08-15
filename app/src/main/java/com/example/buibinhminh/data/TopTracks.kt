package com.example.buibinhminh.data

import com.google.gson.annotations.SerializedName

data class TopTracksResponse(
    @SerializedName("toptracks")
    val toptracks: TopTracksDto
)

data class TopTracksDto(
    @SerializedName("track")
    val tracks: List<TrackDto>
)

data class TrackDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("playcount")
    val playcount: String,
    @SerializedName("listeners")
    val listeners: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("artist")
    val artist: ArtistDto,
    @SerializedName("image")
    val images: List<ImageDto>
)

