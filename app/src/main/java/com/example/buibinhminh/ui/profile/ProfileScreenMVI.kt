package com.example.buibinhminh.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.buibinhminh.ui.profileApp.ProfileHeader
import com.example.buibinhminh.ui.profileApp.ProfileImageBox
import com.example.buibinhminh.ui.profileApp.SuccessDialog

@Composable
fun ProfileScreenMVI(
    viewModel: ProfileViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.profileEvent.collect { event ->
            when (event) {
                ProfileEvent.ShowSuccessMessage -> {
                    viewModel.showSuccessDialog()
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = viewState.currentTheme.color,
        typography = viewState.currentTheme.typography,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ProfileHeader(
                isEditing = viewState.isEditing,
                onEditClick = { viewModel.processIntent(ProfileIntent.ToggleEditMode) },
                currentTheme = viewState.currentTheme,
                onThemeChangeClick = { viewModel.processIntent(ProfileIntent.ToggleTheme) }
            )

            ProfileImageBox(
                isEditing = viewState.isEditing,
                currentImageUri = viewState.profileImageUri,
                onImageChange = { newUri ->
                    viewModel.processIntent(ProfileIntent.UpdateProfileImage(newUri))
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 0.dp)
            ) {
                ProfileInformationFieldMVI(
                    "NAME", "Enter your name...",
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    minLines = 1,
                    regex = "^[a-zA-Z ]*$",
                    errorMessage = "Only characters allowed",
                    value = viewState.name,
                    isValidationEnabled = true,
                    isEditable = viewState.isEditing,
                    isError = viewState.isNameError,
                    onValueChange = { newValue ->
                        viewModel.processIntent(ProfileIntent.UpdateName(newValue))
                    },
                    onValidationChange = { hasError ->
                        viewModel.processIntent(ProfileIntent.SetNameError(hasError))
                    }
                )

                ProfileInformationFieldMVI(
                    "PHONE NUMBER", "Your phone number...",
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    minLines = 1,
                    regex = "^[0-9]*$",
                    errorMessage = "Only number allowed",
                    value = viewState.phoneNumber,
                    isValidationEnabled = true,
                    isEditable = viewState.isEditing,
                    isError = viewState.isPhoneNumberError,
                    onValueChange = { newValue ->
                        viewModel.processIntent(ProfileIntent.UpdatePhoneNumber(newValue))
                    },
                    onValidationChange = { hasError ->
                        viewModel.processIntent(ProfileIntent.SetPhoneNumberError(hasError))
                    }
                )
            }
                ProfileInformationFieldMVI(
                    "UNIVERSITY NAME", "Your university name...",
                    modifier = Modifier.padding(16.dp, 8.dp),
                    minLines = 1,
                    regex = "^[a-zA-Z ]*$",
                    errorMessage = "Only characters allowed",
                    value = viewState.universityName,
                    isValidationEnabled = true,
                    isEditable = viewState.isEditing,
                    isError = viewState.isUniversityNameError,
                    onValueChange = { newValue ->
                        viewModel.processIntent(ProfileIntent.UpdateUniversityName(newValue))
                    },
                    onValidationChange = { hasError ->
                        viewModel.processIntent(ProfileIntent.SetUniversityNameError(hasError))
                    }
                )

                ProfileInformationFieldMVI(
                    "DESCRIBE YOURSELF",
                    "Enter a description about yourself...",
                    modifier = Modifier.padding(16.dp, 8.dp),
                    minLines = 5,
                    value = viewState.description,
                    isValidationEnabled = false,
                    isEditable = viewState.isEditing,
                    isError = false,
                    onValueChange = { newValue ->
                        viewModel.processIntent(ProfileIntent.UpdateDescription(newValue))
                    },
                    onValidationChange = { hasError ->
                        viewModel.processIntent(ProfileIntent.SetUniversityNameError(hasError))
                    }
                )



            Spacer(modifier = Modifier.height(8.dp))

            if (viewState.isEditing) {
                Button(
                    onClick = {
                        viewModel.processIntent(ProfileIntent.SubmitProfile)
                    },
                    enabled = viewState.canSubmit,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(64.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceTint,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    if (viewState.isLoading) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onSecondary, modifier = Modifier.size(24.dp))
                    } else {
                        Text(text = "Submit", fontSize = 18.sp)
                    }
                }
            }
        }
    }

    if (viewState.showSuccessDialog) {
        SuccessDialog(
            onDismissRequest = { viewModel.processIntent(ProfileIntent.DismissSuccessDialog) }
        )
    }
}