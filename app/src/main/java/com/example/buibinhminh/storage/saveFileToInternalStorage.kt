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
        val internalFile = File(context.filesDir, fileName) // tro den internal storage cua ung dung

        responseBody.byteStream().use { inputStream -> // tra ve InputStream tu internet
            internalFile.outputStream().use { outputStream -> // mo ra 1 OutputStream de ghi du lieu vao file
                inputStream.copyTo(outputStream) // sao chep tat ca cac byte dau vao sang luong dau ra de luu file
            }
        } // khoi use se tu dong dong InputStream va OutputStream sau khi xong
        return internalFile.toUri()
    } catch (e: Exception) {
        Log.e("SaveSong", "Error saving song to internal storage: ${e.message}")
        return null
    }
}