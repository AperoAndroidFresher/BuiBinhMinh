package com.example.buibinhminh.ui.finalApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
fun SignUpScreen(
    onSignUpSuccess: (User) -> Unit
) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmed by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    var userNameIsError by remember { mutableStateOf(false) }
    var passwordIsError by remember { mutableStateOf(false) }
    var confirmPasswordIsError by remember { mutableStateOf(false) }
    var emailIsError by remember { mutableStateOf(false) }

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
                text = "Sign up",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp)
            )
        }

        InputField(
            value = userName,
            onValueChange = { userName = it },
            label = "Username",
            isError = userNameIsError,
            errorMessage = "Invalid format",
            leadingIcon = painterResource(id = R.drawable.outline_person_24)
        )

        InputField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            isError = passwordIsError,
            errorMessage = "Invalid format",
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

        InputField(
            value = passwordConfirmed,
            onValueChange = { passwordConfirmed = it },
            label = "Confirm password",
            isError = confirmPasswordIsError,
            errorMessage = "Confirmation password does not match",
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

        InputField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            isError = emailIsError,
            errorMessage = "Invalid format",
            leadingIcon = painterResource(id = R.drawable.outline_mail_24)
        )



        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (userNameIsError) userName = ""
                if (passwordIsError) password = ""
                if (confirmPasswordIsError) passwordConfirmed = ""
                if (emailIsError) email = ""
                userNameIsError = !userName.matches("^[a-zA-Z0-9]+$".toRegex())
                passwordIsError = !password.matches("^[a-zA-Z0-9]+$".toRegex())
                confirmPasswordIsError = password != passwordConfirmed
                emailIsError = ! email.matches("^[a-zA-Z0-9._-]+@apero\\.vn$".toRegex())
                if (!userNameIsError && !passwordIsError && !confirmPasswordIsError && !emailIsError) {
                    val newUser = User(userName, email, password)
                    onSignUpSuccess(newUser)
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
            Text(text = "Sign up", fontSize = 16.sp)
        }


    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen {}
}