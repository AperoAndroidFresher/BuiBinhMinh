package com.example.buibinhminh.ui.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buibinhminh.R
import com.example.buibinhminh.data.Song

@Composable
fun SongPlayerInformation(song: Song) {
    Column(
        modifier = Modifier
            .padding(32.dp, 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.song),
                contentDescription = "Song",
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }

        Text(
            text = song.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = song.artist,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}