package com.example.buibinhminh.storage

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.buibinhminh.data.Song
import kotlinx.serialization.json.Json
import java.io.File

fun readSavedSongsFromInternalStorage(context: Context): List<Song> {
    try {
        val jsonFile = File(context.filesDir, "remote_songs_metadata.json")
        val jsonString = jsonFile.readText()
        return Json.decodeFromString(jsonString)
    }  catch (e: Exception) {
        Log.e(TAG, "Failed to read saved metadata: ${e.message}")
        return emptyList()
    }
}