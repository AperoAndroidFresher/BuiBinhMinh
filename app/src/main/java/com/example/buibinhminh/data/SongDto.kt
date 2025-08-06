package com.example.buibinhminh.data

import android.net.Uri
import com.google.gson.annotations.SerializedName
import androidx.core.net.toUri

data class SongDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("artist")
    val artist: String,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("path")
    val path: String
)

fun SongDto.toSong(): Song {
    val uniqueId = this.path.hashCode().toLong()

    return Song(
        id = uniqueId,
        title = this.title,
        artist = this.artist,
        duration = this.duration.toLongOrNull() ?: 0L,
        albumId = uniqueId,
        contentUri = this.path.toUri()
    )
}