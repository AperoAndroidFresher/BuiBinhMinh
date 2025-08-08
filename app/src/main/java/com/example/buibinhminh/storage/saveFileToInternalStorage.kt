package com.example.buibinhminh.storage

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.buibinhminh.data.Song
import okhttp3.ResponseBody
import java.io.File

fun saveFileToInternalStorage(context: Context, song: Song, responseBody: ResponseBody): Uri? {
    try {
        val fileName = "${song.title}.mp3"
        val internalFile = File(context.filesDir, fileName)

        responseBody.byteStream().use { inputStream ->
            internalFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return internalFile.toUri()
    } catch (e: Exception) {
        Log.e("SaveSong", "Error saving song to internal storage: ${e.message}")
        return null
    }
}