package com.example.buibinhminh.ui.signup

import com.example.buibinhminh.data.User

sealed interface SignUpEvent {
    data class ShowToast(val message: String) : SignUpEvent
    data class NavigateToLoginScreen(val newUser: User) : SignUpEvent
}