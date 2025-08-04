package com.example.buibinhminh.ui.authen.signup

sealed interface SignUpIntent {
    data class UpdateUsername(val username: String) : SignUpIntent
    data class UpdatePassword(val password: String) : SignUpIntent
    data class UpdatePasswordConfirmed(val passwordConfirmed: String) : SignUpIntent
    data class UpdateEmail(val email: String) : SignUpIntent
    data object TogglePasswordVisibility : SignUpIntent
    data object SignUpClicked : SignUpIntent
}