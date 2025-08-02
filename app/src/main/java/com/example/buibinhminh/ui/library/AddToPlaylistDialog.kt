package com.example.buibinhminh.ui.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buibinhminh.R
import com.example.buibinhminh.data.Playlist

@Composable
fun AddToPlaylistDialog(
    playlists: List<Playlist>,
    onDismiss: () -> Unit,
    onPlaylistSelected: (Playlist) -> Unit,
    onAddPlaylist: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Choose playlist",
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (playlists.isEmpty()) {
                    Text(
                        "You don't have any playlists. Click the \"+\" button to add",
                        color = Color.Gray,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .width(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    IconButton(onClick = onAddPlaylist) {
                        Icon(
                            painter = painterResource(R.drawable.outline_add_box_24),
                            contentDescription = "Add new playlist",
                            tint = Color(0xff00c2cb),
                            modifier = Modifier.size(100.dp)
                        )
                    }
                } else {
                    LazyColumn {
                        items(playlists) { playlist ->
                            PlaylistItem(
                                playlist = playlist,
                                onClick = { onPlaylistSelected(playlist) }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {},
        containerColor = Color(0xff1e1e1e)
    )
}


