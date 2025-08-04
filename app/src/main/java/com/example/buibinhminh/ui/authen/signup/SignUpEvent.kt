package com.example.buibinhminh.ui.authen.signup

import com.example.buibinhminh.database.entity.UserEntity

sealed interface SignUpEvent {
    data class ShowToast(val message: String) : SignUpEvent
    data class NavigateToLoginScreen(val newUser: UserEntity) : SignUpEvent
}