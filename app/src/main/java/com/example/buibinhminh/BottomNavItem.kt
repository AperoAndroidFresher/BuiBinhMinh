package com.example.buibinhminh

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.NavKey

interface BottomNavItem {
    @get:DrawableRes
    val iconResId: Int
    val tittle: String
    val screen: Screen
}

data object Home: NavKey, BottomNavItem {
    override val iconResId: Int = R.drawable.outline_home_24
    override val tittle: String = "Home"
    override val screen: Screen = Screen.Home
}

data object Library: NavKey, BottomNavItem {
    override val iconResId: Int = R.drawable.outline_library_music_24
    override val tittle: String = "Library"
    override val screen: Screen = Screen.Library
}

data object Playlist: NavKey, BottomNavItem {
    override val iconResId: Int = R.drawable.outline_playlist_play_24
    override val tittle: String = "Playlist"
    override val screen: Screen = Screen.Playlist
}