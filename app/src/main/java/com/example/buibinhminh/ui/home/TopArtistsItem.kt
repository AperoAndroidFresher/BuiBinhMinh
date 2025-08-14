package com.example.buibinhminh.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.buibinhminh.R
import com.example.buibinhminh.data.Artist

@Composable
fun TopArtistItem(
    artist: Artist
) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(4.dp),
    ) {
        AsyncImage(
            model = artist.imageUrl,
            contentDescription = "Track Image",
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.song),
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = artist.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xff666666),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.TopStart).padding(16.dp)
        )
    }
}