package com.example.buibinhminh.helper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun RequestStoragePermission(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33+
        Manifest.permission.READ_MEDIA_AUDIO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE // API < 33
    }

    var hasPermission by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        hasPermission = isGranted
        if (!isGranted) {
            Log.w("MP3_DEBUG", "Permission denied: $permission")
        }
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            Log.d("MP3_DEBUG", "Requesting permission: $permission")
            launcher.launch(permission)
        } else {
            Log.d("MP3_DEBUG", "Permission already granted: $permission")
        }
    }

    if (hasPermission) {
        content()
    } else {
        Text("Please grant storage permission to view MP3s.", modifier = Modifier.padding(16.dp))
    }
}