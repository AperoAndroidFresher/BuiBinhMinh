package com.example.buibinhminh.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.buibinhminh.R
import com.example.buibinhminh.data.Track

private val trackColors = listOf(
    Color(0xFF777777), Color(0xFFFA7777), Color(0xFF4462FF), Color(0xFF14FF00),
    Color(0xFFE231FF), Color(0xFF00FFFF), Color(0xFFFB003C), Color(0xFFF2A5FF)
)

@Composable
fun TopTrackItem(
    track: Track,
    itemIndex: Int
) {
    Box(
        modifier = Modifier
            .size(175.dp)
            .padding(4.dp),
    ) {
        AsyncImage(
            model = track.imageUrl,
            contentDescription = "Track Image",
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.song),
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = track.name,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = Color(0xff666666),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
        ) {
            Row(
                modifier = Modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_hearing_24),
                    contentDescription = "Play count icon",
                    tint = Color(0xff666666),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.padding(end = 4.dp))
                Text(
                    text = "${track.playcount}",
                    fontSize = 12.sp,
                    color = Color(0xff666666),
                )
            }
            Row(
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_artist_24),
                    contentDescription = "Play count icon",
                    tint = Color(0xff666666),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.padding(end = 4.dp))
                Text(
                    text = track.artistName,
                    fontSize = 12.sp,
                    color = Color(0xff666666),
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(
                        color = trackColors[itemIndex % trackColors.size]
                    )
            )
        }

    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun TopTrackItemPreview() {
    val dummyTrack = Track(
        name = "Bohemian Rhapsody",
        artistName = "Queen",
        playcount = 12345678,
        imageUrl = "https://lastfm.freetls.fastly.net/i/u/300x300/174s/2a96cbd8b46e442fc41c2b86b821562f.png"
    )
    TopTrackItem(track = dummyTrack, itemIndex = 0)
}