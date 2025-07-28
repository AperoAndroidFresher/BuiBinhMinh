package com.example.buibinhminh.ui.profileApp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buibinhminh.R
import com.example.buibinhminh.ui.theme.ThemeData
import com.example.buibinhminh.ui.theme.darkTheme
import com.example.buibinhminh.ui.theme.lightTheme

@Composable
fun ProfileHeader(
    isEditing: Boolean,
    onEditClick: () -> Unit,
    currentTheme: ThemeData,
    onThemeChangeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 32.dp, 16.dp, 16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (currentTheme) {
            lightTheme -> Icon(
                painter = painterResource(id = R.drawable.baseline_dark_mode_24),
                contentDescription = "Change Theme",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterStart)
                    .padding(start = 4.dp)
                    .clickable { onThemeChangeClick() }
            )
            darkTheme -> Icon(
                painter = painterResource(id = R.drawable.outline_light_mode_24),
                contentDescription = "Change Theme",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterStart)
                    .padding(start = 4.dp)
                    .clickable { onThemeChangeClick() }
            )
        }

        Text(
            text = "MY INFORMATION",
            fontWeight = FontWeight.W500,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp)
        )
        if (!isEditing) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_edit_note_24),
                contentDescription = "Chỉnh sửa thông tin",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterEnd)
                    .padding(start = 4.dp)
                    .clickable { onEditClick() }
            )
        }
    }
}