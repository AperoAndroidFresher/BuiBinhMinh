package com.example.buibinhminh.ui.musicApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.buibinhminh.R
import com.example.buibinhminh.data.Song

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