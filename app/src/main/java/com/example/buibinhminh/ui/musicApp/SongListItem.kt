package com.example.buibinhminh.ui.musicApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.buibinhminh.data.Song
import com.example.buibinhminh.helper.formatDuration
import com.example.buibinhminh.helper.getEmbeddedThumbnail

@Composable
fun SongListItem(
    song: Song,
    onDeleteClick: (Song) -> Unit
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row (
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painter,
                contentDescription = "Song",
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(8.dp, 18.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = song.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artist,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatDuration(song.duration),
                fontSize = 17.sp,
                color = Color.White
            )
            SongOptionButton(
                song = song,
                onDeleteClick = onDeleteClick,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
