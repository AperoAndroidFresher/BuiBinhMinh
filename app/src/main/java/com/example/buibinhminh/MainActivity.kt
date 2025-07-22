package com.example.buibinhminh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableOpenTarget
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.buibinhminh.ui.theme.BuiBinhMinhTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuiBinhMinhTheme {
                ProfileEdit()
            }
        }
    }
}


@Composable
fun InformationField (title: String, placeholder: String, modifier: Modifier = Modifier, minLines : Int, content: String = ""){
    var information = content
    Column (
        modifier = modifier
    ){
        Text(
            text = title,
            color = Color.Gray,
            fontWeight = FontWeight.W500,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        OutlinedTextField(
            value = information,
            onValueChange = { information = it },
            placeholder = {
                Text(
                   text = placeholder,
                    color = Color.Gray,
                    fontSize = 12.sp
                ) },
            minLines = minLines,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
    }
}
@Composable
fun ProfileNoEdit () {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight().background(Color(0xfff0f4f9))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
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
            Icon(
                painter = painterResource(id = R.drawable.baseline_edit_note_24), // <-- Sử dụng painterResource để tải vector asset
                contentDescription = "Chỉnh sửa thông tin",
                tint = Color.Black, // Màu của icon
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterEnd)
                    .padding(start = 4.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.meo),
            contentDescription = "Avatar",
            modifier = Modifier.size(200.dp)
                .padding(25.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ){
            InformationField("NAME","Enter your name...", modifier = Modifier.weight(1f).padding(8.dp),1)
            InformationField("PHONE NUMBER","Your phone number...", modifier = Modifier.weight(1f).padding(8.dp),1)
        }
        InformationField("UNIVERSITY NAME","Your university name...", modifier = Modifier.padding(16.dp),1)
        InformationField("DESCRIBE YOURSELF","Enter a description about yourself...", modifier = Modifier.padding(16.dp), 10)
    }
}
@Composable
fun ProfileEdit () {
    var showDialog by remember { mutableStateOf(false) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight().background(Color(0xFFF0F4F9))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
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
        }
        Image(
            painter = painterResource(id = R.drawable.meo),
            contentDescription = "Avatar",
            modifier = Modifier.size(200.dp)
                .padding(25.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ){
            InformationField("NAME","Enter your name...", modifier = Modifier.weight(1f).padding(8.dp),1, "BuiBinhMinh")
            InformationField("PHONE NUMBER","Your phone number...", modifier = Modifier.weight(1f).padding(8.dp),1, "06969696996")
        }
        InformationField("UNIVERSITY NAME","Your university name...", modifier = Modifier.padding(16.dp),1, "Ahihi")
        InformationField("DESCRIBE YOURSELF","Enter a description about yourself...", modifier = Modifier.padding(16.dp), 10)

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(64.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(text = "Submit", fontSize = 18.sp)
        }
    }
    if (showDialog) {
        SuccessDialog(onDismissRequest = {
            showDialog = false
        })

        LaunchedEffect(Unit) {
            delay(2000)
            showDialog = false 
        }
    }
}

@Composable
fun SuccessDialog(onDismissRequest: () -> Unit){
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .size(350.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.success_icon),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(150.dp)
                        .padding(16.dp)
                )

                Text(
                    text = "Success!",
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff1b8c6a),
                    modifier = Modifier.padding(8.dp, 0.dp)
                )

                Text(
                    text = "Your information has been updated!",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
@Preview(
    showBackground = true
)
@Composable
fun DialogPreview() {
    SuccessDialog {}
}
@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)
@Composable
fun ProfileNoEditPreview() {
    ProfileEdit()
}
