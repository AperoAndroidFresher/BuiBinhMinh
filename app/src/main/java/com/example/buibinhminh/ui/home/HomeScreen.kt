package com.example.buibinhminh.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.buibinhminh.ui.profile.ProfileViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit = {},
    onNavigateToAlbums: () -> Unit,
    profileViewModel: ProfileViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel()
) {
    val albums by homeViewModel.albums.collectAsState()
    val tracks by homeViewModel.tracks.collectAsState()
    val artists by homeViewModel.artists.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xff121111))
            .padding(16.dp, 32.dp, 16.dp, 16.dp)
    ) {
        HomeHeader(
            onProfileClick = onProfileClick,
            viewModel = profileViewModel
        )
        HomeTitle()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                TopAlbumHome(
                    albums = albums,
                    onSeeAllClick = { onNavigateToAlbums() }
                )
            }
            item {
                TopTracksHome(
                    tracks = tracks,
                    onSeeAllClick = {  }
                )
            }
            item {
                TopArtistsHome(
                    artists = artists,
                    onSeeAllClick = {  }
                )
            }
        }
    }
}