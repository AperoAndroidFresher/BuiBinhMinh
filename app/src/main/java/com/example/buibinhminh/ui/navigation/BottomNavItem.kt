package com.example.buibinhminh.ui.navigation

import androidx.annotation.DrawableRes
import androidx.navigation3.runtime.NavKey
import com.example.buibinhminh.R
import com.example.buibinhminh.ui.navigation.Screen

interface BaseBottomNavItem {
    @get:DrawableRes
    val iconResId: Int
    val tittle: String
}

interface BottomNavItemWithScreen: BaseBottomNavItem {
    val screen: Screen
}

interface BottomNavItemWithUser: BaseBottomNavItem {
    val screen: (userId: Int) -> Screen
}
data object Home: NavKey, BottomNavItemWithUser  {
    override val iconResId: Int = R.drawable.outline_home_24
    override val tittle: String = "Home"
    override val screen: (userId: Int) -> Screen = { userId -> Screen.Home(userId) }
}

data object Library: NavKey, BottomNavItemWithScreen  {
    override val iconResId: Int = R.drawable.outline_library_music_24
    override val tittle: String = "Library"
    override val screen: Screen = Screen.Library
}

data object Playlist: NavKey, BottomNavItemWithScreen  {
    override val iconResId: Int = R.drawable.outline_playlist_play_24
    override val tittle: String = "Playlist"
    override val screen: Screen = Screen.MyPlaylist
}