package com.example.buibinhminh.ui.authen.login

import com.example.buibinhminh.database.entity.UserEntity

sealed interface LoginEvent { 
    data class ShowToast(val message: String) : LoginEvent
    data class NavigateToHomeScreen(val user: UserEntity) : LoginEvent
    data object NavigateToSignUpScreen : LoginEvent
}