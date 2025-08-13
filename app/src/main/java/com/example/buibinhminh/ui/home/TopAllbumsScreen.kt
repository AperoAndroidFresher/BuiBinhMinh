package com.example.buibinhminh.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.buibinhminh.data.Album

@Composable
fun TopAlbumsScreen(
    homeViewModel: HomeViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val albums by homeViewModel.albums.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff121111))
    ) {
        TopListHeader(
            title = "Top Albums",
            onBackClick = onBackClick
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(albums) { album ->
                TopAlbumsItem(album = album)
            }
        }
    }
}