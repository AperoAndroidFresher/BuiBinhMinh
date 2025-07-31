package com.example.buibinhminh.ui.playlist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.buibinhminh.helper.RequestStoragePermission

@Composable
fun PlaylistScreenMVI(
    viewModel: PlaylistViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    RequestStoragePermission {
        LaunchedEffect(Unit) {
            Log.d("MVI_DEBUG", "PlaylistScreen: LaunchedEffect - sending LoadSongs intent")
            viewModel.processIntent(PlaylistIntent.LoadSongs)
        }

        Column(
            modifier = Modifier
                .background(Color(0xff121111))
                .padding(top = 24.dp)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    Text("Loading songs...", modifier = Modifier.padding(16.dp), color = Color.White)
                }
                state.error != null -> {
                    Text("Error: ${state.error}", modifier = Modifier.padding(16.dp), color = Color.Red)
                }
                state.isEmpty -> {
                    Text("No MP3 files found on this device.", modifier = Modifier.padding(16.dp), color = Color.White)
                }
                else -> {
                    PlaylistHeader(
                        isGrid = state.isGridView,
                        onToggleGrid = { viewModel.processIntent(PlaylistIntent.ToggleViewMode) }, // Send intent
                        onSortClick = { }
                    )

                    if (!state.isGridView) {
                        PlaylistListView(
                            songs = state.songs,
                            onDeleteClick = { song -> viewModel.processIntent(PlaylistIntent.DeleteSong(song)) } // Send intent
                        )
                    } else {
                        PlaylistGrid(
                            songs = state.songs,
                            onDeleteClick = { song -> viewModel.processIntent(PlaylistIntent.DeleteSong(song)) } // Send intent
                        )
                    }
                }
            }
        }
    }
}

