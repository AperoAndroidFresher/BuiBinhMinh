package com.example.buibinhminh.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
fun HomeScreen(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xff121111))
            .padding(16.dp, 32.dp, 16.dp, 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "HOME",
            fontWeight = FontWeight.W500,
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.outline_person_24),
            contentDescription = "Show Profile",
            tint = Color.White,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.TopEnd)
                .padding(start = 4.dp)
                .clickable { onProfileClick() }
        )
    }
}