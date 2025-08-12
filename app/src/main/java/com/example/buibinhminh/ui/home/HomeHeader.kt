package com.example.buibinhminh.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buibinhminh.R

@Preview(
    showBackground = true,
)
@Composable
fun HomeHeader(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ){
        Row (
            modifier = Modifier
                .align(Alignment.CenterStart)
        ){
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )

            Column (
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "Welcome back !",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "User",
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.sharp_settings_24),
            contentDescription = "Setting",
            tint = Color.White,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterEnd)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun HomeTitle(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.outline_crown_24),
            contentDescription = "Ranking",
            tint = Color.Yellow,
            modifier = Modifier.size(32.dp)
        )

        Text(
            text = "Rankings",
            fontSize = 27.sp,
            fontWeight = FontWeight.W700,
            color = Color(0xff00C2CB),
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun TopBoardHeader(
    title : String = "Top Albums"
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(8.dp)
    ){
        Text(
            text = title,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Text(
            text = "See all",
            color = Color(0xff00C2CB),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}