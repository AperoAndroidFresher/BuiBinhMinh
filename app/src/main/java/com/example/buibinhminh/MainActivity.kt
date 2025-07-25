package com.example.buibinhminh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.buibinhminh.ui.profileApp.ProfileScreen
import com.example.buibinhminh.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                ProfileScreen()
//                PlaylistScreen()
            }
        }
    }
}


