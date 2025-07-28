package com.example.buibinhminh

sealed interface Screen {
    data object Home: Screen
    data object Playlist: Screen
    data object Library: Screen
    data object Profile: Screen
    data class Login(val username: String = "", val password: String = "") : Screen
    data class SignUp(val username: String = "", val password: String = "", val email: String = "") : Screen
}