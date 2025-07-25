package com.example.buibinhminh.ui.musicApp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.buibinhminh.data.Song

@Composable
fun PlaylistGrid(
    songs: List<Song>,
    onDeleteClick: (Song) -> Unit,
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
            SongGridItem(
                song = song,
                onDeleteClick = onDeleteClick
            )
        }
    }
}