package com.example.buibinhminh.data

import android.net.Uri
import com.example.buibinhminh.database.entity.SongEntity

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val duration: Long,
    val albumId: Long,
    val contentUri: Uri
)
fun Song.toSongEntity(): SongEntity {
    return SongEntity(
        id = this.id,
        title = this.title,
        artist = this.artist,
        duration = this.duration,
        albumId = this.albumId,
        contentUri = this.contentUri
    )
}