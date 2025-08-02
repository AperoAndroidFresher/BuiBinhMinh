package com.example.buibinhminh.ui.library

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buibinhminh.R
import com.example.buibinhminh.data.MenuOption
import com.example.buibinhminh.data.Playlist
import com.example.buibinhminh.ui.shared.GenericOptionMenu

@Composable
fun PlaylistItem(
    playlist: Playlist,
    onClick: () -> Unit,
    options: List<MenuOption> = emptyList()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.song),
            contentDescription = "Playlist image",
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(8.dp, 18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = playlist.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${playlist.songs.size} song${if (playlist.songs.size != 1) "s" else ""}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (options.isNotEmpty()) {
            GenericOptionMenu(
                options = options
            )
        }
    }
}