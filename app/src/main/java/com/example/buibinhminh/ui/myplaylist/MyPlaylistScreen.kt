package com.example.buibinhminh.ui.myplaylist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buibinhminh.R
import com.example.buibinhminh.data.MenuOption
import com.example.buibinhminh.data.Playlist
import com.example.buibinhminh.ui.library.PlaylistItem

@Composable
fun MyPlaylistScreen(
    viewModel: MyPlaylistViewModel,
    onPlaylistClick : (Playlist) -> Unit
) {
    val state by viewModel.state.collectAsState()

    val myPlaylistOptionsProvider = remember {
        { playlist: Playlist ->
            listOf(
                MenuOption(
                    title = "Remove playlist",
                    icon = R.drawable.outline_remove_circle_outline_24,
                    onClick = {
                        viewModel.processIntent(
                            MyPlaylistIntent.OnDeletePlaylist(
                                playlistId = playlist.id
                            )
                        )
                    }
                ),
                MenuOption(
                    title = "Rename",
                    icon = R.drawable.outline_edit_24,
                    onClick = {
                        viewModel.processIntent(
                            MyPlaylistIntent.OnRenameClick(
                                playlist = playlist
                            )
                        )
                    }
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .background(Color(0xff121111))
            .padding(top = 16.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "My Playlist",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "Change to Grid",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { viewModel.processIntent(MyPlaylistIntent.OnAddClick) }
                )
            }

        }
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            itemsIndexed(
                state.playlists,
                key = { index , playlist -> playlist.id }
            ) { index, playlist ->
                val playlistOptions = remember(playlist) { myPlaylistOptionsProvider(playlist) }

                PlaylistItem(
                    playlist = playlist,
                    onClick = {
                        onPlaylistClick(playlist)
                    },
                    options = playlistOptions
                )
            }
        }
        if (state.isCreatingPlaylist) {
            PlaylistNameDialog(
                title = "New Playlist",
                value = state.newPlaylistName,
                onValueChange = {
                    viewModel.processIntent(MyPlaylistIntent.OnPlaylistNameChange(it))
                },
                onDismiss = {
                    viewModel.processIntent(MyPlaylistIntent.OnCancelCreate)
                },
                onConfirm = {
                    viewModel.processIntent(MyPlaylistIntent.OnCreateConfirm)
                },
                confirmText = "Create"
            )
        }
        if (state.isRenamingPlaylist) {
            PlaylistNameDialog(
                title = "Rename Playlist",
                value = state.newPlaylistName,
                onValueChange = {
                    viewModel.processIntent(MyPlaylistIntent.OnPlaylistNameChange(it))
                },
                onDismiss = {
                    viewModel.processIntent(MyPlaylistIntent.OnCancelRename)
                },
                onConfirm = {
                    viewModel.processIntent(MyPlaylistIntent.OnRenamePlaylistConfirm)
                },
                confirmText = "Rename"
            )
        }
    }
}