package com.example.buibinhminh.ui.playlistSong

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
import com.example.buibinhminh.ui.player.SongPlayerViewModel

@Composable
fun PlaylistGrid(
    songs: List<Song>,
    optionsProvider: (Song) -> List<MenuOption>,
    playerViewModel: SongPlayerViewModel,
    nowPlayingSongId: Long?,
    isPlaying: Boolean,
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
            val isCurrentSong = song.id == nowPlayingSongId

            SongGridItem(
                song = song,
                options = playlistOptions,
                playerViewModel = playerViewModel,
                isPlaying = isCurrentSong && isPlaying,
                isCurrentSong = isCurrentSong
            )
        }
    }
}