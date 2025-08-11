package com.example.buibinhminh.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.buibinhminh.data.Song

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SongPlayer(
    song : Song = Song(1,"Anh khong lam gi dau Anh khong lam gi dau Anh khong lam gi dau ","test", 50000, 1, "???".toUri()),
    progress: Float = 0.5f,
    currentTime: Long = 25000,
    totalDuration: Long = 50000,
    isPlaying: Boolean = false,
    isShuffling: Boolean = false,
    repeatMode: RepeatMode = RepeatMode.OFF,
    onBackClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
    onPlayPauseClick: () -> Unit = {},
    onSkipPreviousClick: () -> Unit = {},
    onSkipNextClick: () -> Unit = {},
    onShuffleClick: () -> Unit = {},
    onRepeatClick: () -> Unit = {},
    onSliderChange: (Float) -> Unit = {}
){
    Column (
        modifier = Modifier
            .background(Color(0xff121111))
            .padding(top = 16.dp)
            .fillMaxSize()
    ) {
        SongPlayerHeader(
            onBackClick = onBackClick,
            onCloseClick = onCloseClick
        )

        SongPlayerInformation(song = song)

        SongPlayerControl(
            progress = progress,
            currentTime = currentTime,
            totalDuration = totalDuration,
            isPlaying = isPlaying,
            isShuffling = isShuffling,
            repeatMode = repeatMode,
            onPlayPauseClick = onPlayPauseClick,
            onSkipPreviousClick = onSkipPreviousClick,
            onSkipNextClick = onSkipNextClick,
            onShuffleClick = onShuffleClick,
            onRepeatClick = onRepeatClick,
            onSliderChange = onSliderChange
        )
    }
}