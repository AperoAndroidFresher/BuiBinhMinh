package com.example.buibinhminh.database.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.buibinhminh.database.entity.PlaylistEntity
import com.example.buibinhminh.database.entity.UserEntity

data class UserPlaylists(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val playlists: List<PlaylistEntity>
)