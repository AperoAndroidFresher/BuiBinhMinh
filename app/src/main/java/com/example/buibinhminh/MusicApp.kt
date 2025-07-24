package com.example.buibinhminh

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Song(val id: Int, val songName: String, val authorName: String, val duration: String)

fun generateSongs(count: Int): List<Song> {
    return List(count) { i ->
        Song(i, "Song title ${i + 1}", "Singer ${i + 1}", "03:${(i % 60).toString().padStart(2, '0')}")
    }
}
@Composable
fun SongOptionButton(
    song: Song,
    onDeleteClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = { showMenu = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Option",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = showMenu,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.background(Color(0xff292929)),
            onDismissRequest = { showMenu = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        "Remove from playlist",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                onClick = {
                    onDeleteClick(song)
                    showMenu = false
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.outline_remove_circle_outline_24),
                        contentDescription = "Option",
                        modifier = Modifier
                            .padding(8.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        "Share (coming soon)",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                onClick = {
                    showMenu = false
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.rounded_share_24),
                        contentDescription = "Option",
                        modifier = Modifier
                            .padding(8.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            )
        }
    }
}
@Composable
fun SongListItem(
    song: Song,
    onDeleteClick: (Song) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.song),
                contentDescription = "Song",
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp, 18.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = song.songName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = song.authorName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = song.duration,
                fontSize = 17.sp,
                color = Color.White
            )
            SongOptionButton(
                song = song,
                onDeleteClick = onDeleteClick,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun SongGridItem(
    song: Song,
    onDeleteClick: (Song) -> Unit)
{
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.song),
                contentDescription = "Song",
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            SongOptionButton(
                song = song,
                onDeleteClick = onDeleteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f))
            )
        }

        Text(
            text = song.songName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = song.authorName,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = song.duration,
            fontSize = 17.sp,
            color = Color.White,
        )
    }
}

@Composable
fun Playlist() {
    val songs = remember { mutableStateListOf<Song>().apply { addAll(generateSongs(20)) } }
    var isGrid by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(Color(0xff121111))
            .padding(top = 24.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "My Playlist",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (!isGrid) {
                    Image(
                        painter = painterResource(id = R.drawable.rounded_border_all_24),
                        contentDescription = "Change to Grid",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { isGrid = true }
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.outline_format_list_bulleted_24),
                        contentDescription = "Change to List",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { isGrid = false }
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.outline_lowercase_24),
                    contentDescription = "Sort",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }

        if (!isGrid) {
            LazyColumn(
                modifier = Modifier.padding(16.dp, 0.dp)
            ) {
                itemsIndexed(
                    songs,
                    key = { index, song -> song.id }
                ) { index, song ->
                    SongListItem(
                        song = song,
                        onDeleteClick = { selectedSong ->
                            songs.remove(selectedSong)
                        }
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                itemsIndexed(
                    songs,
                    key = { index, song -> song.id }
                ) { index, song ->
                    SongGridItem(
                        song = song,
                        onDeleteClick = { selectedSong ->
                            songs.remove(selectedSong)
                        }
                    )
                }
            }
        }
    }
}

//@Preview(
//    showBackground = true
//)
//@Composable
//fun SongListItemPreview() {
//    SongGridItem(Song("Test", "Example", "03:30"))
//}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)
@Composable
fun PlaylistReview() {
    Playlist()
}