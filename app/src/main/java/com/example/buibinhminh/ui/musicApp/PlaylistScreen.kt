package com.example.buibinhminh.ui.musicApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buibinhminh.data.Song
import com.example.buibinhminh.helper.RequestStoragePermission
import com.example.buibinhminh.helper.getAllMp3Files


@Composable
fun PlaylistScreen() {
    val context = LocalContext.current
    var songs = remember { mutableStateListOf<Song>() }
    var isGrid by remember { mutableStateOf(false) }

    RequestStoragePermission {
        LaunchedEffect(Unit) {
            songs.addAll(getAllMp3Files(context))
        }

        if (songs.isEmpty()) {
            Text("No MP3 files found on this device.", modifier = Modifier.padding(16.dp))
        } else {
            Column(
                modifier = Modifier
                    .background(Color(0xff121111))
                    .padding(top = 24.dp)
                    .fillMaxSize()
            ) {
                PlaylistHeader(
                    isGrid = isGrid,
                    onToggleGrid = { isGrid = !isGrid },
                    onSortClick = { }
                )

                if (!isGrid) {
                    PlaylistListView(
                        songs = songs,
                        onDeleteClick = { selectedSong ->
                            songs.remove(selectedSong)
                        }
                    )
                } else {
                    PlaylistGrid(
                        songs = songs,
                        onDeleteClick = { selectedSong ->
                            songs.remove(selectedSong)
                        }
                    )
                }
            }
        }
    }
}

//@Preview(
//    showBackground = true
//)
//@Composable
//fun SongListItemPreview() {
//    SongGridItem(Song("Test", "Example", "03:30"))
//}

//@Preview(
//    showBackground = true,
//    device = Devices.PIXEL_4_XL,
//    showSystemUi = true
//)
//@Composable
//fun PlaylistReview() {
//    PlaylistScreen()
//}