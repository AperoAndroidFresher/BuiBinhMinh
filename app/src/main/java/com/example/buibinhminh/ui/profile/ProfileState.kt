package com.example.buibinhminh.ui.profile

import android.net.Uri
import com.example.buibinhminh.ui.theme.ThemeData
import com.example.buibinhminh.ui.theme.darkTheme

data class ProfileState(
    val isEditing: Boolean = false,
    val showSuccessDialog: Boolean = false,
    val currentTheme: ThemeData = darkTheme,

    val profileId: Int? = null,
    val name: String = "",
    val phoneNumber: String = "",
    val universityName: String = "",
    val description: String = "",
    val profileImageUri: Uri? = null,

    val isNameError: Boolean = false,
    val isPhoneNumberError: Boolean = false,
    val isUniversityNameError: Boolean = false,
    val isLoading: Boolean = false
) {
    val canSubmit: Boolean
        get() = !isNameError && !isPhoneNumberError && !isUniversityNameError && !isLoading
}