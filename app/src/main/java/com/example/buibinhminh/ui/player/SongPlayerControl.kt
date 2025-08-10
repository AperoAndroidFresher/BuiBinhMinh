package com.example.buibinhminh.ui.player

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buibinhminh.R
import com.example.buibinhminh.helper.formatDuration

@Composable
fun SongPlayerControl(
    progress: Float,
    currentTime: Long,
    totalDuration: Long,
    isPlaying: Boolean,
    isShuffling: Boolean,
    isRepeating: Boolean,
    onPlayPauseClick: () -> Unit,
    onSkipPreviousClick: () -> Unit,
    onSkipNextClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onRepeatClick: () -> Unit,
    onSliderChange: (Float) -> Unit
) {
    var isSeeking by remember { mutableStateOf(false) }
    var tempSliderPosition by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(progress) {
        if (!isSeeking) {
            tempSliderPosition = progress
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Slider(
            value = tempSliderPosition,
            onValueChange = {
                tempSliderPosition = it
                isSeeking = true
            },
            onValueChangeFinished = {
                onSliderChange(tempSliderPosition)
                isSeeking = false
            },
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFBB86FC),
                activeTrackColor = Color(0xFFBB86FC),
                inactiveTrackColor = Color.Gray
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDuration(currentTime),
                color = Color.Gray,
                fontSize = 14.sp
            )
            Text(
                text = formatDuration(totalDuration),
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.rounded_shuffle_24),
                contentDescription = "Shuffle",
                tint = if (!isShuffling) Color.Gray else Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onShuffleClick() }
            )

            Icon(
                painter = painterResource(id = R.drawable.baseline_skip_previous_24),
                contentDescription = "Skip previous",
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onSkipPreviousClick() }
            )

            Icon(
                painter = painterResource(id = if (isPlaying) R.drawable.outline_play_circle_24 else R.drawable.outline_pause_circle_24),
                contentDescription = "Play/Pause",
                tint = Color(0xFFBB86FC),
                modifier = Modifier
                    .size(64.dp)
                    .clickable { onPlayPauseClick() }
            )

            Icon(
                painter = painterResource(id = R.drawable.baseline_skip_next_24),
                contentDescription = "Skip next",
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onSkipNextClick() }
            )

            Icon(
                painter = painterResource(id = R.drawable.rounded_repeat_24),
                contentDescription = "Repeat",
                tint = if (!isRepeating) Color.Gray else Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onRepeatClick() }
            )
        }
    }
}