package com.example.buibinhminh.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SongPlayerScreen(
    playerViewModel: SongPlayerViewModel,
    onBackClick: () -> Unit
) {
    val playerState by playerViewModel.nowPlayingState.collectAsState()
    val song = playerState.nowPlayingSong

    if (song != null) {
        SongPlayer(
            song = song,
            progress = playerState.songProgress,
            currentTime = playerState.currentTime,
            totalDuration = song.duration,
            isPlaying = playerState.isPlaying,
            isShuffling = playerState.isShuffling,
            repeatMode = playerState.repeatMode,
            onBackClick = onBackClick,
            onCloseClick = {
                playerViewModel.processIntent(SongPlayerIntent.CloseSong)
                onBackClick()
            },
            onPlayPauseClick = {
                if (playerState.isPlaying) {
                    playerViewModel.processIntent(SongPlayerIntent.PauseSong)
                } else {
                    playerViewModel.processIntent(SongPlayerIntent.ResumeSong)
                }
            },
            onSkipPreviousClick = {
                playerViewModel.processIntent(SongPlayerIntent.SkipPrevious)
            },
            onSkipNextClick = {
                playerViewModel.processIntent(SongPlayerIntent.SkipNext)
            },
            onShuffleClick = {
                playerViewModel.processIntent(SongPlayerIntent.ToggleShuffle)
            },
            onRepeatClick = {
                playerViewModel.processIntent(SongPlayerIntent.ToggleRepeat)
            },
            onSliderChange = { newProgress ->
                val newPosition = (newProgress * song.duration).toLong()
                playerViewModel.processIntent(SongPlayerIntent.SeekTo(newPosition))
            }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff121111)),
            contentAlignment = Alignment.Center
        ) {
            Text("Not found song", color = Color.Gray)
        }
    }
}