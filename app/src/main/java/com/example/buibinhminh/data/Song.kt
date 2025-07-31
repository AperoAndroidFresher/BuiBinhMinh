package com.example.buibinhminh.data

import android.net.Uri

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val duration: Long,
    val albumId: Long,
    val contentUri: Uri
)
