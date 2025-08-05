package com.example.buibinhminh.ui.authen.signup

data class SignUpState(
    val username: String = "",
    val password: String = "",
    val passwordConfirmed: String = "",
    val email: String = "",
    val passwordVisible: Boolean = false,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val emailError: String? = null,
    val isLoading: Boolean = false
)