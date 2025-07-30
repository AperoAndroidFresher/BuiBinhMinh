package com.example.buibinhminh.ui.login

sealed interface LoginEvent { 
    data class ShowToast(val message: String) : LoginEvent
    data object NavigateToHomeScreen : LoginEvent
    data object NavigateToSignUpScreen : LoginEvent
}