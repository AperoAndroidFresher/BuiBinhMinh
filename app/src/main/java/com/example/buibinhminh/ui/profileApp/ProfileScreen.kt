package com.example.buibinhminh.ui.profileApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buibinhminh.R
import com.example.buibinhminh.data.Student
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen () {
    var isEditing by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var universityName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var isNameError by remember { mutableStateOf(false) }
    var isPhoneNumberError by remember { mutableStateOf(false) }
    var isUniversityNameError by remember { mutableStateOf(false) }

    val canSubmit = !isNameError && !isPhoneNumberError && !isUniversityNameError

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight().background(Color(0xFFF0F4F9))
    ) {
        ProfileHeader(
            isEditing = isEditing,
            onEditClick = { isEditing = true }
        )

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
                .padding(horizontal = 8.dp, vertical = 0.dp)
        ){
            ProfileInformationField("NAME", "Enter your name...",
                modifier = Modifier.weight(1f).padding(8.dp), 1,
                "^[a-zA-Z]*$", "Only characters allowed" ,name, true, isEditing,
                onValidationChange = { hasError -> isNameError = hasError })
            ProfileInformationField("PHONE NUMBER","Your phone number...",
                modifier = Modifier.weight(1f).padding(8.dp),1,
                "^[0-9]*$","Only number allowed", phoneNumber, true, isEditing,
                onValidationChange = { hasError -> isPhoneNumberError = hasError })
        }
        ProfileInformationField("UNIVERSITY NAME","Your university name...",
            modifier = Modifier.padding(16.dp, 8.dp), 1,
            "^[a-zA-Z]*$","Only characters allowed" , universityName, true, isEditing,
            onValidationChange = { hasError -> isUniversityNameError = hasError })
        ProfileInformationField("DESCRIBE YOURSELF","Enter a description about yourself...", modifier = Modifier.padding(16.dp, 8.dp), 10, description, isValidationEnabled =  false, isEditable = isEditing)

        Spacer(modifier = Modifier.height(8.dp))

        if (isEditing){
            Button(
                onClick = {
                    if(canSubmit) {
                        val student = Student(
                            name = name,
                            phoneNumber = phoneNumber,
                            universityName = universityName,
                            description = description
                        )

                        println("Thông tin sinh viên đã tạo:")
                        println(student)

                        showDialog = true
                    } },
                enabled = canSubmit,
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
    }

    if (showDialog) {
        SuccessDialog(onDismissRequest = {
            showDialog = false
            isEditing = false
        })

        LaunchedEffect(Unit) {
            delay(2000)
            showDialog = false
            isEditing = false
        }
    }
}


//@Preview(
//    showBackground = true
//)
//@Composable
//fun DialogPreview() {
//    SuccessDialog {}
//}
@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    showSystemUi = true
)
@Composable
fun ProfileNoEditPreview() {
    ProfileScreen()
}