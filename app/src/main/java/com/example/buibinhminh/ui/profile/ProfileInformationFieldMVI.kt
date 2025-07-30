package com.example.buibinhminh.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileInformationFieldMVI(
    title: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    minLines: Int,
    regex: String = "",
    errorMessage: String = "",
    value: String,
    isValidationEnabled: Boolean = true,
    isEditable: Boolean = true,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    onValidationChange: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.W500,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (isEditable) {
                    onValueChange(newValue)
                    if (isValidationEnabled) {
                        val hasError = newValue.isNotEmpty() && !newValue.matches(regex.toRegex())
                        onValidationChange(hasError)
                    } else {
                        onValidationChange(false)
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                disabledContainerColor = MaterialTheme.colorScheme.onSecondary,
                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                errorLeadingIconColor = MaterialTheme.colorScheme.error,
                errorTrailingIconColor = MaterialTheme.colorScheme.error,
                errorSupportingTextColor = MaterialTheme.colorScheme.error,
                errorTextColor = MaterialTheme.colorScheme.error
            ),
            readOnly = !isEditable,
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(errorMessage)
                }
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            },
            minLines = minLines,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
    }
}