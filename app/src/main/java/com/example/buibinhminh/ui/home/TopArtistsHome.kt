package com.example.buibinhminh.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.buibinhminh.data.Artist

@Composable
fun TopArtistsHome(
    artists: List<Artist>,
    onSeeAllClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopBoardHeader(
            title = "Top Artist",
            onSeeAllClick = onSeeAllClick
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(artists.take(5)) {artist ->
                TopArtistItem(artist)
            }
        }

    }
}