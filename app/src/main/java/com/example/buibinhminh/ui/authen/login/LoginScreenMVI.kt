package com.example.buibinhminh.ui.authen.login

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buibinhminh.R
import com.example.buibinhminh.ui.shared.InputField

@Composable
fun LoginScreenMVI(
    viewModel: LoginViewModel,
    initialUsername: String = "",
    initialPassword: String = "",
    onLoginSuccess: () -> Unit,
    onSignUpClicked: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(initialUsername, initialPassword) {
        if (initialUsername.isNotEmpty()) {
            viewModel.processIntent(LoginIntent.UpdateUsername(initialUsername))
        }
        if (initialPassword.isNotEmpty()) {
            viewModel.processIntent(LoginIntent.UpdatePassword(initialPassword))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loginEvent.collect { event ->
            when (event) {
                is LoginEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                LoginEvent.NavigateToHomeScreen -> {
                    onLoginSuccess()
                }
                LoginEvent.NavigateToSignUpScreen -> {
                    onSignUpClicked()
                }
            }
        }
    }

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
            value = viewState.username,
            onValueChange = { viewModel.processIntent(LoginIntent.UpdateUsername(it)) },
            label = "Username",
            leadingIcon = painterResource(id = R.drawable.outline_person_24)
        )

        InputField(
            value = viewState.password,
            onValueChange = { viewModel.processIntent(LoginIntent.UpdatePassword(it)) },
            label = "Password",
            leadingIcon = painterResource(id = R.drawable.outline_lock_24),
            visualTransformation = if (viewState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val imageResource = if (viewState.passwordVisible)
                    R.drawable.outline_visibility_24
                else R.drawable.outline_visibility_off_24

                IconButton(onClick = { viewModel.processIntent(LoginIntent.TogglePasswordVisibility) }) {
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
                checked = viewState.isRememberMe,
                onCheckedChange = {  },
            )
            Text(
                text = "Remember me",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        Button(
            onClick = { viewModel.processIntent(LoginIntent.LoginClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF06a0b5)
            ),
            enabled = !viewState.isLoading
        ) {
            if (viewState.isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(text = "Log in", fontSize = 16.sp)
            }
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
                        viewModel.processIntent(LoginIntent.SignUpClicked)
                    }
            )
        }

    }
}