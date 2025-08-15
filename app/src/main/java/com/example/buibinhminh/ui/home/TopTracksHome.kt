package com.example.buibinhminh.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.buibinhminh.data.Track

@Composable
fun TopTracksHome(
    tracks: List<Track>,
    onSeeAllClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopBoardHeader(
            title = "Top Tracks",
            onSeeAllClick = onSeeAllClick
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(tracks.take(5)) { index, track ->
                TopTrackItem(track = track, itemIndex = index)
            }
        }
    }

    Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
}
