package com.example.buibinhminh.ui.finalApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xff121111))
            .padding(16.dp, 32.dp, 16.dp, 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "LIBRARY",
            fontWeight = FontWeight.W500,
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
            // .padding(horizontal = 0.dp, vertical = 0.dp)
        )
    }
}