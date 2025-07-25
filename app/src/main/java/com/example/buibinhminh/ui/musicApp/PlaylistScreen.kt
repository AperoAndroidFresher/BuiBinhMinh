package com.example.buibinhminh.ui.musicApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buibinhminh.data.Song

fun generateSongs(count: Int): List<Song> {
    return List(count) { i ->
        Song(i, "Song title ${i + 1}", "Singer ${i + 1}", "03:${(i % 60).toString().padStart(2, '0')}")
    }
}

@Composable
fun PlaylistScreen() {
    val songs = remember { mutableStateListOf<Song>().apply { addAll(generateSongs(20)) } }
    var isGrid by remember { mutableStateOf(false) }

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

//@Preview(
//    showBackground = true
//)
//@Composable
//fun SongListItemPreview() {
//    SongGridItem(Song("Test", "Example", "03:30"))
//}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)
@Composable
fun PlaylistReview() {
    PlaylistScreen()
}