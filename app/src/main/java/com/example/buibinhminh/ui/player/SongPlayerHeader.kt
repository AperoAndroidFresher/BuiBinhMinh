package com.example.buibinhminh.ui.player

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buibinhminh.R

@Composable
fun SongPlayerHeader(
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(32.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Icon(
            painter = painterResource(id = R.drawable.rounded_arrow_back),
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.TopStart)
                .padding(start = 4.dp)
                .clickable { onBackClick() }
        )
        Text(
            text = "Now playing",
            fontWeight = FontWeight.W500,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.outline_close_24),
            contentDescription = "Close",
            tint = Color.White,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.TopEnd)
                .padding(start = 4.dp)
                .clickable { onCloseClick() }
        )
    }
}
