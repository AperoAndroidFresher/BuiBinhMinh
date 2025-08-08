package com.example.buibinhminh.storage

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.buibinhminh.data.Song
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

fun saveSongMetadata(context: Context, songs: List<Song>) {
    try {
        val jsonFile = File(context.filesDir, "remote_songs_metadata.json")
        val jsonString = Json.encodeToString(songs)
        jsonFile.writeText(jsonString)
    } catch (e: IOException) {
        Log.e(TAG, "Failed to save metadata: ${e.message}")
    }
}