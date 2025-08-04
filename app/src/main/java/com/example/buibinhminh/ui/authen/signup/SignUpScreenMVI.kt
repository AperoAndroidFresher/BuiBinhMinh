package com.example.buibinhminh.ui.authen.signup

import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.buibinhminh.R
import com.example.buibinhminh.data.User
import com.example.buibinhminh.database.entity.UserEntity
import com.example.buibinhminh.ui.shared.InputField

@Composable
fun SignUpScreenMVI(
    viewModel: SignUpViewModel,
    onSignUpSuccess: (UserEntity) -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.signUpEvent.collect { event ->
            when (event) {
                is SignUpEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is SignUpEvent.NavigateToLoginScreen -> {
                    onSignUpSuccess(event.newUser)
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
                text = "Sign up",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp)
            )
        }

        InputField(
            value = viewState.username,
            onValueChange = { viewModel.processIntent(SignUpIntent.UpdateUsername(it)) },
            label = "Username",
            isError = viewState.usernameError != null,
            errorMessage = viewState.usernameError.toString(),
            leadingIcon = painterResource(id = R.drawable.outline_person_24)
        )

        InputField(
            value = viewState.password,
            onValueChange = { viewModel.processIntent(SignUpIntent.UpdatePassword(it)) },
            label = "Password",
            isError = viewState.passwordError != null,
            errorMessage = viewState.passwordError.toString(),
            leadingIcon = painterResource(id = R.drawable.outline_lock_24),
            visualTransformation = if (viewState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val imageResource = if (viewState.passwordVisible)
                    R.drawable.outline_visibility_24
                else R.drawable.outline_visibility_off_24

                IconButton(onClick = { viewModel.processIntent(SignUpIntent.TogglePasswordVisibility) }) {
                    Icon(
                        painter = painterResource(id = imageResource),
                        contentDescription = "Toggle password visibility"
                    )
                }
            }
        )

        InputField(
            value = viewState.passwordConfirmed,
            onValueChange = { viewModel.processIntent(SignUpIntent.UpdatePasswordConfirmed(it)) },
            label = "Confirm password",
            isError = viewState.confirmPasswordError != null,
            errorMessage = viewState.confirmPasswordError.toString(),
            leadingIcon = painterResource(id = R.drawable.outline_lock_24),
            visualTransformation = if (viewState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val imageResource = if (viewState.passwordVisible)
                    R.drawable.outline_visibility_24
                else R.drawable.outline_visibility_off_24

                IconButton(onClick = { viewModel.processIntent(SignUpIntent.TogglePasswordVisibility) }) {
                    Icon(
                        painter = painterResource(id = imageResource),
                        contentDescription = "Toggle password visibility"
                    )
                }
            }
        )

        InputField(
            value = viewState.email,
            onValueChange = { viewModel.processIntent(SignUpIntent.UpdateEmail(it)) },
            label = "Email",
            isError = viewState.emailError != null,
            errorMessage = viewState.emailError.toString(),
            leadingIcon = painterResource(id = R.drawable.outline_mail_24)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.processIntent(SignUpIntent.SignUpClicked) },
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
                Text(text = "Sign up", fontSize = 16.sp)
            }
        }
    }
}