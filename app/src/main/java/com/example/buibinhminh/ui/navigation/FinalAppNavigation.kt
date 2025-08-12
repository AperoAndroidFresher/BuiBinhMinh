package com.example.buibinhminh.ui.navigation

import android.app.Application
import android.util.Log
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.buibinhminh.R
import com.example.buibinhminh.database.AppDatabase
import com.example.buibinhminh.helper.formatDuration
import com.example.buibinhminh.repository.PlaylistRepository
import com.example.buibinhminh.repository.ProfileRepository
import com.example.buibinhminh.repository.UserRepository
import com.example.buibinhminh.ui.authen.AuthViewModel
import com.example.buibinhminh.ui.authen.login.LoginScreenMVI
import com.example.buibinhminh.ui.authen.login.LoginViewModel
import com.example.buibinhminh.ui.authen.signup.SignUpScreenMVI
import com.example.buibinhminh.ui.authen.signup.SignUpViewModel
import com.example.buibinhminh.ui.home.HomeScreen
import com.example.buibinhminh.ui.library.LibraryScreen
import com.example.buibinhminh.ui.library.LibraryViewModel
import com.example.buibinhminh.ui.myplaylist.MyPlaylistScreen
import com.example.buibinhminh.ui.myplaylist.MyPlaylistViewModel
import com.example.buibinhminh.ui.player.SongPlayerIntent
import com.example.buibinhminh.ui.player.SongPlayerScreen
import com.example.buibinhminh.ui.player.SongPlayerViewModel
import com.example.buibinhminh.ui.playlistSong.PlaylistScreenMVI
import com.example.buibinhminh.ui.playlistSong.PlaylistViewModel
import com.example.buibinhminh.ui.profile.ProfileScreenMVI
import com.example.buibinhminh.ui.profile.ProfileViewModel

@Composable
fun FinalAppNavigation() {
    val context = LocalContext.current.applicationContext
    val db = remember { AppDatabase.getInstance(context) }

    val authViewModel: AuthViewModel = viewModel()
    val playerViewModel: SongPlayerViewModel = viewModel()
    val currentUser by authViewModel.currentUser.collectAsState()
    val userId = currentUser?.id

    val userRepository = remember { UserRepository(db.userDao()) }
    val profileRepository = remember { ProfileRepository(db.profileDao()) }
    val playlistRepository = remember { PlaylistRepository(db.playlistDao(), db.songDao()) }

    val backStack = remember { mutableStateListOf<Screen>() }
    val bottomNavItems = listOf(Home, Library, Playlist)
    var isNavReady by remember { mutableStateOf(false) }

    LaunchedEffect(currentUser) {
        Log.d("FinalAppNavigation", "currentUser changed: ${currentUser?.id}")
        if (currentUser != null && backStack.isEmpty()) {
            backStack.add(Screen.Home(userId = currentUser!!.id))
        } else if (currentUser == null && backStack.isEmpty()) {
            backStack.add(Screen.Login())
        }
        isNavReady = true
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

    }
    Scaffold(
        containerColor = Color(0xFF222222),
        bottomBar = {
            val currentScreen = backStack.lastOrNull()
            val showBottomNav =
                backStack.lastOrNull() !is Screen.Login
                        && backStack.lastOrNull() !is Screen.SignUp
                        && backStack.lastOrNull() !is Screen.Profile

            if (showBottomNav) {
                Column {
                    // Current song
                    if (backStack.lastOrNull() != Screen.Player) {
                        SongProgressBar(
                            playerViewModel = playerViewModel,
                            onProgressBarClick = {
                                backStack.add(Screen.Player)
                            }
                        )
                    }
                    //Navigation bar
                    NavigationBar(
                        containerColor = Color(0xFF1A1A1A),
                    ) {
                        bottomNavItems.forEach { item ->
                            if (userId != null) {
                                val selected = when (currentScreen) {
                                    is Screen.Home -> item is Home
                                    is Screen.Library -> item is Library
                                    is Screen.MyPlaylist -> item is Playlist
                                    else -> false
                                }

                                NavigationBarItem(
                                    selected = selected,
                                    onClick = {
                                        when (item) {
                                            is BottomNavItemWithUser -> backStack.add(
                                                item.screen(
                                                    userId
                                                )
                                            )

                                            is BottomNavItemWithScreen -> backStack.add(item.screen)
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

            }
        }
    ) { innerPadding ->
        if (isNavReady) {
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
                            initialUsername = username,
                            initialPassword = password,
                            onSignUpClicked = {
                                backStack.add(Screen.SignUp())
                            },
                            onLoginSuccess = { user ->
                                authViewModel.setLoggedInUser(user)
                                backStack.clear()
                                backStack.add(Screen.Home(userId = user.id))
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
                    entry<Screen.Home> { (userId) ->
                        HomeScreen(
                            onProfileClick = {
                                backStack.add(Screen.Profile(userId = userId))
                            }
                        )
                    }
                    entry<Screen.MyPlaylist> {
                        if (userId != null) {
                            val viewModel =
                                viewModel { MyPlaylistViewModel(playlistRepository, userId) }
                            MyPlaylistScreen(
                                viewModel = viewModel,
                                onPlaylistClick = { playlist ->
                                    backStack.add(Screen.Playlist(playlist))
                                }
                            )
                        }
                    }
                    entry<Screen.Playlist> { screen ->
                        val factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                @Suppress("UNCHECKED_CAST")
                                return PlaylistViewModel(
                                    playlistRepository,
                                    screen.playlist.id
                                ) as T
                            }
                        }
                        val viewModel: PlaylistViewModel = viewModel(
                            key = screen.playlist.id.toString(),
                            factory = factory
                        )
                        PlaylistScreenMVI(
                            playlist = screen.playlist,
                            viewModel = viewModel
                        )
                    }

                    entry<Screen.Library> {
                        if (userId != null) {
                            val viewModel = viewModel {
                                LibraryViewModel(
                                    context as Application,
                                    playlistRepository,
                                    userId
                                )
                            }
                            LibraryScreen(
                                viewModel = viewModel,
                                onCreatePlaylist = {
                                    backStack.add(Screen.MyPlaylist)
                                }
                            )
                        }
                    }
                    entry<Screen.Player> {
                        SongPlayerScreen(
                            playerViewModel = playerViewModel,
                            onBackClick = { backStack.removeLastOrNull() }
                        )
                    }
                    entry<Screen.Profile> { (userId) ->
                        val viewModel = remember { ProfileViewModel(userId, profileRepository) }
                        ProfileScreenMVI(viewModel = viewModel)
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
}

@Composable
fun SongProgressBar(
    playerViewModel: SongPlayerViewModel,
    onProgressBarClick: () -> Unit
) {
    val playerState by playerViewModel.nowPlayingState.collectAsState()

    if (playerState.nowPlayingSong == null) {
        return
    }

    val song = playerState.nowPlayingSong
    val isPlaying = playerState.isPlaying
    val progress = playerState.songProgress

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onProgressBarClick() }
    ) {

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isPlaying) R.drawable.round_pause_24 else R.drawable.baseline_play_arrow_24
                    ),
                    contentDescription = "Play/Pause",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(40.dp)
                        .clickable {
                            if (isPlaying) {
                                playerViewModel.processIntent(SongPlayerIntent.PauseSong)
                            } else {
                                playerViewModel.processIntent(SongPlayerIntent.ResumeSong)
                            }
                        }
                )

                Text(
                    text = song?.title ?: "Not found",
                    fontWeight = FontWeight.W500,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(8.dp)
                )

            }
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDuration(song?.duration ?: 0L),
                    color = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.outline_close_24),
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            playerViewModel.processIntent(SongPlayerIntent.CloseSong)
                        }
                )
            }
        }
    }
}