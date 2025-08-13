package com.example.buibinhminh.data

import com.google.gson.annotations.SerializedName

data class TopAlbumsResponse(
    @SerializedName("topalbums")
    val topAlbums: TopAlbumsDto
)

data class TopAlbumsDto(
    @SerializedName("album")
    val albumList: List<AlbumDto>
)

data class AlbumDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("artist")
    val artist: ArtistDto,
    @SerializedName("image")
    val image: List<ImageDto>
)

fun TopAlbumsResponse.toAlbumList(): List<Album> {
    return this.topAlbums.albumList.map { albumDto ->
        val imageUrl = albumDto.image.find { it.size == "medium" }?.url ?: ""
        Album(
            name = albumDto.name,
            artistName = albumDto.artist.name,
            imageUrl = imageUrl
        )
    }
}