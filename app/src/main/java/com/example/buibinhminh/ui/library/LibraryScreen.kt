package com.example.buibinhminh.ui.library

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.buibinhminh.R
import com.example.buibinhminh.data.MenuOption
import com.example.buibinhminh.data.Song
import com.example.buibinhminh.helper.RequestStoragePermission
import com.example.buibinhminh.ui.animation.LoadingAnimation
import com.example.buibinhminh.ui.player.SongPlayerIntent
import com.example.buibinhminh.ui.player.SongPlayerViewModel
import com.example.buibinhminh.ui.playlistSong.PlaylistListView

@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = viewModel(),
    playerViewModel: SongPlayerViewModel = viewModel(),
    onCreatePlaylist: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val playerState by playerViewModel.nowPlayingState.collectAsState()
    val playlists by viewModel.playlists.collectAsState()

    var selectedButton by remember { mutableStateOf("Local") }

    RequestStoragePermission {
        LaunchedEffect(selectedButton) {
            Log.d("MVI_DEBUG", "Library: LaunchedEffect - sending LoadSongs intent")
            if (selectedButton == "Local") {
                viewModel.processIntent(LibraryIntent.LoadLocalSongs)
            } else {
                viewModel.processIntent(LibraryIntent.LoadRemoteSongs)
            }
        }

        if (state.showAddToPlaylistDialog && state.selectedSongForPlaylist != null) {
            AddToPlaylistDialog(
                playlists = playlists,
                onDismiss = { viewModel.processIntent(LibraryIntent.HideAddToPlaylistDialog) },
                onPlaylistSelected = { playlist ->
                    viewModel.processIntent(
                        LibraryIntent.AddToPlaylist(
                            song = state.selectedSongForPlaylist!!,
                            playlistId = playlist.id
                        )
                    )
                },
                onAddPlaylist = {
                    onCreatePlaylist()
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff121111))
                .padding(16.dp, 32.dp, 16.dp, 16.dp),
        ) {
            Text(
                text = "Library",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = {
                        selectedButton = "Local"
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedButton == "Local") Color(0xff00c2cb) else Color(
                            0xff1e1e1e
                        )
                    )
                ) {
                    Text(
                        text = "Local",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Button(
                    onClick = {
                        selectedButton = "Remote"
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedButton == "Remote") Color(0xff00c2cb) else Color(
                            0xff1e1e1e
                        )
                    )
                ) {
                    Text(
                        text = "Remote",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LoadingAnimation(modifier = Modifier.size(200.dp).align(Alignment.Center))
                    }
                }
                state.error != null && state.songs.isEmpty()-> {
//                    Text("Error: ${state.error}", modifier = Modifier.padding(16.dp), color = Color.Red)
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.nothing),
                            contentDescription = "No internet connection",
                            modifier = Modifier.size(100.dp)
                        )

                        Text(
                            text = state.error!!,
                            fontSize = 28.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp, 24.dp)
                        )

                        Button(
                            onClick = {
                                viewModel.processIntent(LibraryIntent.LoadRemoteSongs)
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(text = "Try again")
                        }
                    }
                }
                state.isEmpty -> {
                    Text("No MP3 files found on this device.", modifier = Modifier.padding(16.dp), color = Color.White)
                }
                else -> {
                    val libraryOptionsProvider = remember {
                        { song: Song ->
                            listOf(
                                MenuOption(
                                    title = "Add to playlist",
                                    icon = R.drawable.outline_playlist_add_24,
                                    onClick = {
                                        viewModel.processIntent(LibraryIntent.ShowAddToPlaylistDialog(song))
                                    }
                                ),
                                MenuOption(
                                    title = "Share",
                                    icon = R.drawable.rounded_share_24,
                                    onClick = {  }
                                )
                            )
                        }
                    }

                    PlaylistListView(
                        songs = state.songs,
                        optionsProvider = libraryOptionsProvider,
                        playerViewModel = playerViewModel,
                        nowPlayingSongId = playerState.nowPlayingSong?.id,
                        isPlaying = playerState.isPlaying,
                        onSongClick = { selectedSong ->
                            playerViewModel.processIntent(
                                SongPlayerIntent.SetQueueAndPlay(state.songs, selectedSong)
                            )
                        }
                    )
                }
            }
        }
    }
}

