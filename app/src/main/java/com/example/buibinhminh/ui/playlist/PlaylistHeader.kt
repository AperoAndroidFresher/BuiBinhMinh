package com.example.buibinhminh.ui.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buibinhminh.R

@Composable
fun PlaylistHeader(
    isGrid: Boolean,
    onToggleGrid: () -> Unit,
    onSortClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
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
                        .clickable { onToggleGrid() }
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.outline_format_list_bulleted_24),
                    contentDescription = "Change to List",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { onToggleGrid() }
                )
            }
            Image(
                painter = painterResource(id = R.drawable.outline_lowercase_24),
                contentDescription = "Sort",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .clickable { onSortClick() }
            )
        }
    }
}
