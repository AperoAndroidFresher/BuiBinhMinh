package com.example.buibinhminh.ui.player

import com.example.buibinhminh.data.Song

data class SongPlayerState (
    val nowPlayingSong: Song? = null,
    val isPlaying: Boolean = false,
    val songProgress: Float = 0.0f,
    val currentTime: Long = 0L
)
