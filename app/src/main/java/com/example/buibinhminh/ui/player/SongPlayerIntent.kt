package com.example.buibinhminh.ui.player

import com.example.buibinhminh.data.Song

sealed interface SongPlayerIntent {
    data class PlaySong(val song: Song) : SongPlayerIntent
    data object PauseSong : SongPlayerIntent
    data object ResumeSong : SongPlayerIntent
    data class SeekTo(val position: Long) : SongPlayerIntent
    data class SetQueueAndPlay(val songs: List<Song>, val startSong: Song) : SongPlayerIntent
    data object SkipNext : SongPlayerIntent
    data object SkipPrevious : SongPlayerIntent
}