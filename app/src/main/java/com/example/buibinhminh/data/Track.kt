package com.example.buibinhminh.data

data class Track(
    val name: String,
    val artistName: String,
    val playcount: Int,
    val imageUrl: String
)

fun TopTracksResponse.toTrackList(): List<Track> {
    return this.toptracks.tracks.map { trackDto ->
        val imageUrl = trackDto.images.find { it.size == "extralarge" }?.url ?: ""
        Track(
            name = trackDto.name,
            artistName = trackDto.artist.name,
            playcount = trackDto.playcount.toIntOrNull() ?: 0,
            imageUrl = imageUrl
        )
    }
}