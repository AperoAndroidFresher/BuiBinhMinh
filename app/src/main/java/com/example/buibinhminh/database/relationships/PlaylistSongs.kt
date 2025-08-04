package com.example.buibinhminh.database.relationships

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.buibinhminh.database.entity.PlaylistEntity
import com.example.buibinhminh.database.entity.PlaylistSongEntity
import com.example.buibinhminh.database.entity.SongEntity

data class PlaylistSongs(
    @Embedded val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            PlaylistSongEntity::class,
            parentColumn = "playlistId",
            entityColumn = "songId"
        )
    )
    val songs: List<SongEntity>
)