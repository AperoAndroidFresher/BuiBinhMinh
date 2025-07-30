package com.example.buibinhminh.ui.profile

import android.net.Uri

sealed interface ProfileIntent {
    data object ToggleEditMode : ProfileIntent
    data object ToggleTheme : ProfileIntent
    data class UpdateProfileImage(val uri: Uri?) : ProfileIntent
    data class UpdateName(val name: String) : ProfileIntent
    data class UpdatePhoneNumber(val phoneNumber: String) : ProfileIntent
    data class UpdateUniversityName(val universityName: String) : ProfileIntent
    data class UpdateDescription(val description: String) : ProfileIntent
    data class SetNameError(val hasError: Boolean) : ProfileIntent
    data class SetPhoneNumberError(val hasError: Boolean) : ProfileIntent
    data class SetUniversityNameError(val hasError: Boolean) : ProfileIntent
    data object SubmitProfile : ProfileIntent
    data object DismissSuccessDialog : ProfileIntent
}