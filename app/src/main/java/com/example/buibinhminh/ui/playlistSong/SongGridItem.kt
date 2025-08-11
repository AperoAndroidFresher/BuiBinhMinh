package com.example.buibinhminh.ui.playlistSong

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.buibinhminh.R
import com.example.buibinhminh.data.MenuOption
import com.example.buibinhminh.data.Song
import com.example.buibinhminh.helper.formatDuration
import com.example.buibinhminh.helper.getEmbeddedThumbnail
import com.example.buibinhminh.ui.animation.PlayingAnimation
import com.example.buibinhminh.ui.player.SongPlayerIntent
import com.example.buibinhminh.ui.player.SongPlayerViewModel
import com.example.buibinhminh.ui.shared.GenericOptionMenu

@Composable
fun SongGridItem(
    song: Song,
    options: List<MenuOption>,
    playerViewModel: SongPlayerViewModel,
    isPlaying: Boolean,
    isCurrentSong: Boolean,
    onSongClick: (Song) -> Unit
) {
    val context = LocalContext.current
    val thumbnailBitmap = remember(song.id) {
        getEmbeddedThumbnail(song.contentUri, context)
    }

    val painter = if (thumbnailBitmap != null) {
        rememberAsyncImagePainter(model = thumbnailBitmap)
    } else {
        painterResource(id = R.drawable.song)
    }

    val backgroundColor = if (isCurrentSong) Color(0xFF333333) else Color.Transparent

    Column(
        modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .padding(16.dp)
            .background(backgroundColor)
            .clickable {
                if (isCurrentSong) {
                    if (isPlaying) {
                        playerViewModel.processIntent(SongPlayerIntent.PauseSong)
                    } else {
                        playerViewModel.processIntent(SongPlayerIntent.ResumeSong)
                    }
                } else {
                    onSongClick(song)
                    playerViewModel.processIntent(SongPlayerIntent.PlaySong(song))
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(150.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painter,
                contentDescription = "Song",
                modifier = Modifier.fillMaxSize()
            )
            if (isCurrentSong && isPlaying) {
                PlayingAnimation(
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .align(Alignment.Center)
                )
            }
            GenericOptionMenu(
                options = options,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f))
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
        Text(
            text = formatDuration(song.duration),
            fontSize = 17.sp,
            color = Color.White,
        )
    }
}
