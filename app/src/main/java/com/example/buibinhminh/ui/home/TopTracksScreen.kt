package com.example.buibinhminh.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TopTracksScreen(
    homeViewModel: HomeViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val tracks by homeViewModel.tracks.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff121111))
    ) {
        TopListHeader(
            title = "Top Tracks",
            onBackClick = onBackClick
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(16.dp, 0.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(tracks) { index, track ->
                TopTrackItem(
                    track = track,
                    itemIndex = index
                )
            }
        }
    }
}