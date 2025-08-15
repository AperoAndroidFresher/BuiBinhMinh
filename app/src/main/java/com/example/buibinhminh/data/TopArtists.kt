package com.example.buibinhminh.data

import com.google.gson.annotations.SerializedName

data class TopArtistsResponse(
    @SerializedName("artists")
    val artists: ArtistsDto
)

data class ArtistsDto(
    @SerializedName("artist")
    val artistList: List<ArtistDto>
)

fun TopArtistsResponse.toArtistList(): List<Artist> {
    return this.artists.artistList.map { artistDto ->
        val imageUrl = artistDto.images?.find { it.size == "extralarge" }?.url ?: ""
        Artist(
            name = artistDto.name,
            imageUrl = imageUrl
        )
    }
}
