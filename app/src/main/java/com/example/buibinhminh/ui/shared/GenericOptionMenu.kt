package com.example.buibinhminh.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.buibinhminh.data.MenuOption

@Composable
fun GenericOptionMenu(
    options: List<MenuOption>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Option",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.background(Color(0xff292929)),
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = option.icon),
                            contentDescription = option.title,
                            modifier = Modifier
                                .padding(8.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    },
                    text = {
                        Text(
                            option.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = {
                        option.onClick()
                        expanded = false
                    }
                )
            }
        }
    }
}