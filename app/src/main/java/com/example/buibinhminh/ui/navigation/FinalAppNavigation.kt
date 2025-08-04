package com.example.buibinhminh.ui.navigation

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.buibinhminh.data.Playlist
import com.example.buibinhminh.data.User
import com.example.buibinhminh.database.AppDatabase
import com.example.buibinhminh.repository.UserRepository
import com.example.buibinhminh.ui.home.HomeScreen
import com.example.buibinhminh.ui.library.LibraryScreen
import com.example.buibinhminh.ui.library.libraryViewModel
import com.example.buibinhminh.ui.authen.login.LoginScreenMVI
import com.example.buibinhminh.ui.authen.login.LoginViewModel
import com.example.buibinhminh.ui.myplaylist.MyPlaylistScreen
import com.example.buibinhminh.ui.myplaylist.MyPlaylistViewModel
import com.example.buibinhminh.ui.playlistSong.PlaylistScreenMVI
import com.example.buibinhminh.ui.playlistSong.PlaylistViewModel
import com.example.buibinhminh.ui.profile.ProfileScreenMVI
import com.example.buibinhminh.ui.authen.signup.SignUpScreenMVI
import com.example.buibinhminh.ui.authen.signup.SignUpViewModel

@Composable
fun FinalAppNavigation() {
    val context = LocalContext.current.applicationContext
    val db = remember { AppDatabase.getInstance(context) }
    val userRepository = remember { UserRepository(db.userDao()) }

    val defaultPlaylists = listOf(
        Playlist(
            id = 1,
            name = "My Mix",
            songs = emptyList()
        ),
        Playlist(id = 2, name = "Chill", songs = emptyList())
    )
    val sharedPlaylists = remember { mutableStateOf(defaultPlaylists) }
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
                    val viewModel: LoginViewModel = remember {
                        LoginViewModel(userRepository)
                    }
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
                    val viewModel: SignUpViewModel = remember {
                        SignUpViewModel(userRepository)
                    }
                    SignUpScreenMVI(
                        viewModel = viewModel,
                        onSignUpSuccess = { newUserEntity ->
                            backStack.add(
                                Screen.Login(
                                    username = newUserEntity.username,
                                    password = newUserEntity.password
                                )
                            )
                        }
                    )
                }
                entry<Screen.Home> {
                    HomeScreen(
                        onProfileClick = {
                            backStack.clear()
                            backStack.add(Screen.MyPlaylist)
                        }
                    )
                }
                entry<Screen.MyPlaylist>{
                    val viewModel = MyPlaylistViewModel(sharedPlaylists)
                    MyPlaylistScreen(
                        viewModel = viewModel,
                        onPlaylistClick = { playlist ->
                            backStack.add(Screen.Playlist(playlist))
                        }
                    )
                }
                entry<Screen.Playlist>{ screen ->
                    val shared = sharedPlaylists
                    val factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            @Suppress("UNCHECKED_CAST")
                            return PlaylistViewModel(shared, screen.playlist.id) as T
                        }
                    }
                    val viewModel: PlaylistViewModel =
                        viewModel(factory = factory)

                    PlaylistScreenMVI(
                        playlist = screen.playlist,
                        viewModel = viewModel
                    )
                }

                entry<Screen.Library>{
                    val viewModel = libraryViewModel(sharedPlaylists = sharedPlaylists)

                    LibraryScreen(
                        viewModel = viewModel,
                        onCreatePlaylist = {
                            backStack.add(Screen.MyPlaylist)
                        }
                    )
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