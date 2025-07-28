package com.example.buibinhminh.ui.finalApp

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.buibinhminh.Home
import com.example.buibinhminh.Library
import com.example.buibinhminh.Playlist
import com.example.buibinhminh.Screen
import com.example.buibinhminh.data.User
import com.example.buibinhminh.ui.musicApp.PlaylistScreen
import com.example.buibinhminh.ui.profileApp.ProfileScreen

@Composable
fun FinalAppNavigation() {
    val userList = remember { mutableStateOf<List<User>>(emptyList()) }

    val backStack = remember { mutableStateListOf<Screen>(Screen.Login()) }
    val bottomNavItems = listOf(Home, Library, Playlist)
    Scaffold (
        containerColor = Color(0xFF222222),
        bottomBar = {
            val currentScreen = backStack.lastOrNull()
            val showBottomNav = backStack.last() !is Screen.Login && backStack.last() !is Screen.SignUp

            if (showBottomNav) {
                NavigationBar (
                    containerColor = Color(0xFF1A1A1A),

                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentScreen == item.screen
                        NavigationBarItem(
                            selected = false,
                            onClick = {
                                if (currentScreen != item.screen) {
                                    backStack.clear()
                                    backStack.add(item.screen)
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(item.iconResId),
                                    contentDescription = "${item.tittle} icon",
                                    modifier = Modifier.size(24.dp),
                                    tint = if (selected) Color(0xFFBB86FC) else Color.White
                                )
                            },
                            label = {
                                Text(item.tittle, color = Color.White)
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<Screen.Login> { (username, password) ->
                    LoginScreen(
                        userName = username,
                        password = password,
                        userList = userList.value,
                        onSignUpClicked = {
                            backStack.add(Screen.SignUp())
                        },
                        onLoginSucess = {
                            backStack.clear()
                            backStack.add(Screen.Home)
                        }
                    )
                }
                entry<Screen.SignUp> {
                    SignUpScreen(
                        onSignUpSuccess = { newUser ->
                            userList.value = userList.value + newUser
                            backStack.add(
                                Screen.Login(
                                    username = newUser.username,
                                    password = newUser.password
                                )
                            )
                        }
                    )
                }
                entry<Screen.Home> {
                    HomeScreen(
                        onProfileClick = {
                            backStack.clear()
                            backStack.add(Screen.Profile)
                        }
                    )
                }
                entry<Screen.Playlist>{
                    PlaylistScreen()
                }
                entry<Screen.Library>{
                    LibraryScreen()
                }
                entry<Screen.Profile> {
                    ProfileScreen()
                }
            },
            transitionSpec = {
                slideInHorizontally(initialOffsetX = { it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { -it })
            },
            popTransitionSpec = {
                slideInHorizontally(initialOffsetX = { -it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { it })
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}