package com.example.buibinhminh.data

data class Playlist(
    val id: Long,
    val name: String,
    val songs: List<Song> = emptyList()
)


