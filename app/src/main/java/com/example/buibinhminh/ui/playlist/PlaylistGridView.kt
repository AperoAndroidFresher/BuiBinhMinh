package com.example.buibinhminh.ui.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.buibinhminh.data.MenuOption
import com.example.buibinhminh.data.Song

@Composable
fun PlaylistGrid(
    songs: List<Song>,
    optionsProvider: (Song) -> List<MenuOption>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        modifier = modifier
    ) {
        itemsIndexed(
            songs,
            key = { index , song -> song.id }
        ) { index, song ->
            val playlistOptions = remember(song) { optionsProvider(song) }

            SongGridItem(
                song = song,
                options = playlistOptions
            )
        }
    }
}