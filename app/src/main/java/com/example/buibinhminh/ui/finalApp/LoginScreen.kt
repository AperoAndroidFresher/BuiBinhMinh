package com.example.buibinhminh.ui.finalApp

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buibinhminh.R
import com.example.buibinhminh.data.User

@Composable
fun LoginScreen(
    userList: List<User>,
    userName: String? = "",
    password: String? = "",
    onSignUpClicked: () -> Unit,
    onLoginSucess: () -> Unit
) {
    var userName by remember { mutableStateOf(userName) }
    var password by remember { mutableStateOf(password) }

    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }
    var isRemember by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(Color(0xFF121111))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(300.dp)
            )

            Text(
                text = "Login to your account",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp)
                )
        }

        InputField(
            value = userName.toString(),
            onValueChange = { userName = it },
            label = "Username",
            leadingIcon = painterResource(id = R.drawable.outline_person_24)
        )

        InputField(
            value = password.toString(),
            onValueChange = { password = it },
            label = "Password",
            leadingIcon = painterResource(id = R.drawable.outline_lock_24),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val imageResource = if (passwordVisible)
                    R.drawable.outline_visibility_24
                else R.drawable.outline_visibility_off_24

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = imageResource),
                        contentDescription = "Toggle password visibility"
                    )
                }
            }
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isRemember,
                onCheckedChange = { isRemember = it },
            )
            Text(
                text = "Remember me",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        Button(
            onClick = {
                val user = userList.find { it.username == userName && it.password == password }
                if (user != null) {
                    onLoginSucess()
                } else {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF06a0b5)
            )
        ) {
            Text(text = "Log in", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        Row (
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(
                "Don't have an account? ",
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                color = Color.White
            )
            Text(
                "Sign up",
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                color = Color(0xFF61bbc8),
                modifier = Modifier
                    .clickable{
                        onSignUpClicked()
                    }
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {
    LoginScreen(userList = mutableListOf(), onSignUpClicked = {}, onLoginSucess = {})
}