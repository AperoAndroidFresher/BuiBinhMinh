package com.example.buibinhminh.ui.profileApp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
fun ProfileHeader(
    isEditing: Boolean,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 32.dp, 16.dp, 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "MY INFORMATION",
            fontWeight = FontWeight.W400,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp)
        )
        if (!isEditing) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_edit_note_24),
                contentDescription = "Chỉnh sửa thông tin",
                tint = Color.Black,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterEnd)
                    .padding(start = 4.dp)
                    .clickable { onEditClick() }
            )
        }
    }
}