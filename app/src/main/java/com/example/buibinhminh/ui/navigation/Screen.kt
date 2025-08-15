package com.example.buibinhminh.ui.navigation

sealed interface Screen {
    data class Playlist(val playlist: com.example.buibinhminh.data.Playlist): Screen
    data object MyPlaylist: Screen
    data object Library: Screen
    data object Player: Screen
    data object TopAlbums: Screen
    data object TopTracks: Screen
    data object TopArtists: Screen
    data class Home(val userId: Int) : Screen
    data class Profile(val userId: Int) : Screen
    data class Login(val username: String = "", val password: String = "") : Screen
    data class SignUp(val username: String = "", val password: String = "", val email: String = "") : Screen
}