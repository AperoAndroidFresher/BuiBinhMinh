package com.example.buibinhminh.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.buibinhminh.data.Playlist

@Entity(
    tableName = "playlists",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val userId: Int
)

fun PlaylistEntity.toPlaylist(): Playlist {
    return Playlist(id = this.id, name = this.name)
}