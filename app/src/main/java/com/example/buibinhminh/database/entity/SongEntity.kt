package com.example.buibinhminh.database.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity (
    @PrimaryKey val id: Long = 0,
    val title: String,
    val artist: String,
    val duration: Long,
    val albumId: Long,
    val contentUri: Uri
)