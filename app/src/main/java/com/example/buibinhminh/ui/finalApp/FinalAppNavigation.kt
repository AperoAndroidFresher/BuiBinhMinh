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
import com.example.buibinhminh.ui.login.LoginScreenMVI
import com.example.buibinhminh.ui.login.LoginViewModel
import com.example.buibinhminh.ui.musicApp.PlaylistScreen
import com.example.buibinhminh.ui.profile.ProfileScreenMVI
import com.example.buibinhminh.ui.profileApp.ProfileScreen
import com.example.buibinhminh.ui.signup.SignUpScreenMVI

@Composable
fun FinalAppNavigation() {
    val userList = listOf(User("a", "123","a"), User("admin", "admin","b"))
    val updatedUserList = remember { mutableStateOf<List<User>>(userList) }

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
                    val viewModel = LoginViewModel(updatedUserList.value)
                    LoginScreenMVI(
                        viewModel = viewModel,
                        onSignUpClicked = {
                            backStack.add(Screen.SignUp())
                        },
                        onLoginSuccess = {
                            backStack.clear()
                            backStack.add(Screen.Home)
                        }
                    )
                }
                entry<Screen.SignUp> {
                    SignUpScreenMVI(
                        onSignUpSuccess = { newUser ->
                            updatedUserList.value = updatedUserList.value + newUser
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
                    ProfileScreenMVI()
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