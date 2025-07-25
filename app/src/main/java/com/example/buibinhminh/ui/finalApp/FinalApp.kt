package com.example.buibinhminh.ui.finalApp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.buibinhminh.data.User

@Composable
fun FinalApp() {
    val navController = rememberNavController()
    val userList = remember { mutableStateOf<List<User>>(emptyList()) }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                userList = userList.value,
                onSignUpClicked = {
                    navController.navigate("signup")
                }
            )
        }

        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = { newUser ->
                    userList.value = userList.value + newUser
                    navController.navigate("login/${newUser.username}/${newUser.password}")
                }
            )
        }

        composable(
            route = "login/{username}/{password}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            val password = backStackEntry.arguments?.getString("password")
            LoginScreen(
                userList = userList.value,
                userName = username,
                password = password,
                onSignUpClicked = {}
            )
        }
    }
}