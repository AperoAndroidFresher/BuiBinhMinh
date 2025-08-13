package com.example.buibinhminh.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.buibinhminh.data.Album

@Composable
fun TopAlbumHome(
    albums: List<Album>,
    onSeeAllClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopBoardHeader(
            title = "Top Albums",
            onSeeAllClick = onSeeAllClick
        )

        albums.take(6).chunked(2).forEach { rowAlbums ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                for (album in rowAlbums) {
                    TopAlbumsItem(
                        album = album,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
}
