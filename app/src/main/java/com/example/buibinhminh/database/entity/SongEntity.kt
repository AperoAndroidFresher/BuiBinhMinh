package com.example.buibinhminh.database.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.buibinhminh.data.Song

@Entity(tableName = "songs")
data class SongEntity (
    @PrimaryKey val id: Long = 0,
    val title: String,
    val artist: String,
    val duration: Long,
    val albumId: Long,
    val contentUri: Uri
)

fun SongEntity.toSong(): Song {
    return Song(
        id = this.id,
        title = this.title,
        artist = this.artist,
        duration = this.duration,
        albumId = this.albumId,
        contentUri = this.contentUri
    )
}