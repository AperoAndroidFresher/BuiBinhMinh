package com.example.buibinhminh.ui.musicApp

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.buibinhminh.data.Song

@Composable
fun PlaylistListView(
    songs: List<Song>,
    onDeleteClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        itemsIndexed(
            songs,
            key = { index , song -> song.id }
        ) { index, song ->
            SongListItem(
                song = song,
                onDeleteClick = onDeleteClick
            )
        }
    }
}