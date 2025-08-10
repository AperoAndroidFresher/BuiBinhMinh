package com.example.buibinhminh.ui.playlistSong

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.buibinhminh.data.MenuOption
import com.example.buibinhminh.data.Song
import com.example.buibinhminh.ui.player.SongPlayerViewModel

@Composable
fun PlaylistListView(
    songs: List<Song>,
    optionsProvider: (Song) -> List<MenuOption>,
    playerViewModel: SongPlayerViewModel,
    nowPlayingSongId: Long?,
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        itemsIndexed(
            songs,
            key = { index , song -> song.id }
        ) { index, song ->
            val playlistOptions = remember(song) { optionsProvider(song) }
            val isCurrentSong = song.id == nowPlayingSongId

            SongListItem(
                song = song,
                options = playlistOptions,
                playerViewModel = playerViewModel,
                isPlaying = isCurrentSong && isPlaying,
                isCurrentSong = isCurrentSong
            )
        }
    }
}