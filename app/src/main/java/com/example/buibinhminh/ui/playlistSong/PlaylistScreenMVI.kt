package com.example.buibinhminh.ui.playlistSong

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.buibinhminh.R
import com.example.buibinhminh.data.MenuOption
import com.example.buibinhminh.data.Playlist
import com.example.buibinhminh.data.Song

@Composable
fun PlaylistScreenMVI(
    playlist: Playlist,
    viewModel: PlaylistViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.processIntent(PlaylistIntent.SetPlaylist(playlist.songs))
    }

    Column(
        modifier = Modifier
            .background(Color(0xff121111))
            .padding(top = 16.dp)
            .fillMaxSize()
    ) {
        when {
            state.isLoading -> {
                Text(
                    "Loading songs...",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }

            state.error != null -> {
                Text(
                    "Error: ${state.error}",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Red
                )
            }

            state.isEmpty -> {
                Text(
                    "Playlist is empty.",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }

            else -> {
                PlaylistHeader(
                    tittle = playlist.name,
                    isGrid = state.isGridView,
                    onToggleGrid = { viewModel.processIntent(PlaylistIntent.ToggleViewMode) },
                    onSortClick = { }
                )

                val playlistOptionsProvider = remember {
                    { song: Song ->
                        listOf(
                            MenuOption(
                                title = "Remove from playlist",
                                icon = R.drawable.outline_remove_circle_outline_24,
                                onClick = {
                                    viewModel.processIntent(
                                        PlaylistIntent.DeleteSong(
                                            song
                                        )
                                    )
                                }
                            ),
                            MenuOption(
                                title = "Share",
                                icon = R.drawable.rounded_share_24,
                                onClick = { }
                            )
                        )
                    }
                }

                if (!state.isGridView) {
                    PlaylistListView(
                        songs = state.songs,
                        optionsProvider = playlistOptionsProvider
                    )
                } else {
                    PlaylistGrid(
                        songs = state.songs,
                        optionsProvider = playlistOptionsProvider
                    )
                }
            }
        }
    }

}