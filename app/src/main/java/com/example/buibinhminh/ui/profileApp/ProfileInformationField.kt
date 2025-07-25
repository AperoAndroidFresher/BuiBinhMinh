package com.example.buibinhminh.ui.profileApp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileInformationField (
    title: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    minLines : Int,
    regex : String = "",
    errorMessage : String ="",
    initialValue: String = "",
    isValidationEnabled: Boolean = true,
    isEditable: Boolean = true,
    onValidationChange: (Boolean) -> Unit = {}
){
    var information by remember { mutableStateOf(initialValue) }
    var isError by remember { mutableStateOf(false) }

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
            onValueChange = { value ->
                if (isEditable) {
                    information = value
                    if (isValidationEnabled){
                        isError = !value.matches(regex.toRegex())
                        onValidationChange(isError)
                    } else{
                        if (isError) {
                            isError = false
                            onValidationChange(false)
                        }
                    }
                }
            },
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
                ) },
            minLines = minLines,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
    }
}