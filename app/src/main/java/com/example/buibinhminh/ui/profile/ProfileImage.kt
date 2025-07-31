package com.example.buibinhminh.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.buibinhminh.R

@Composable
fun ProfileImageBox(
    isEditing: Boolean,
    currentImageUri: Uri?,
    onImageChange: (Uri?) -> Unit
) {
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImageChange(uri)
    }

    Box{
        val imagePainter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(currentImageUri ?: R.drawable.meo)
                .size(Size(300, 300))
                .crossfade(true)
                .build()
        )
        Image(
            painter = imagePainter,
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .padding(25.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        if (isEditing) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                contentDescription = "Change Image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xbb000000))
                    .padding(7.dp)
                    .align(Alignment.BottomCenter)
                    .clickable {
                        galleryLauncher.launch("image/*")
                    },
                tint = Color.White,
            )
        }
    }
}
