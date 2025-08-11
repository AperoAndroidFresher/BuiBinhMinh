package com.example.buibinhminh.ui.player

import com.example.buibinhminh.data.Song

sealed interface SongPlayerIntent {
    data class PlaySong(val song: Song) : SongPlayerIntent
    object PauseSong : SongPlayerIntent
    object ResumeSong : SongPlayerIntent
    data class SeekTo(val position: Long) : SongPlayerIntent
}