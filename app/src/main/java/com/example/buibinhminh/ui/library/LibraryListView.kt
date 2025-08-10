package com.example.buibinhminh.ui.library

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.buibinhminh.R
import com.example.buibinhminh.data.MenuOption
import com.example.buibinhminh.data.Song
import com.example.buibinhminh.ui.playlistSong.SongListItem

//@Composable
//fun LibraryListView(
//    songs: List<Song>,
//    onShareClick: (Song) -> Unit,
//    onAddToPlaylistClick: (Song) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    LazyColumn(
//        modifier = modifier.padding(horizontal = 16.dp)
//    ) {
//        itemsIndexed(
//            songs,
//            key = { index , song -> song.id }
//        ) { index, song ->
//
//            val libraryOptions = remember(song) {
//                listOf(
//                    MenuOption(
//                        title = "Remove from playlist",
//                        icon = R.drawable.outline_remove_circle_outline_24,
//                        onClick = {  }
//                    ),
//                    MenuOption(
//                        title = "Share",
//                        icon = R.drawable.rounded_share_24,
//                        onClick = { }
//                    )
//                )
//            }
//            SongListItem(
//                song = song,
//                options = libraryOptions
//            )
//        }
//    }
//}