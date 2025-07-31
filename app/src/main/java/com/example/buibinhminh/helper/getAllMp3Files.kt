package com.example.buibinhminh.helper

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.buibinhminh.data.Song
import androidx.core.net.toUri
import java.util.concurrent.TimeUnit

fun getAllMp3Files(context: Context): List<Song> {
    val songs = mutableListOf<Song>()
    val contentResolver: ContentResolver = context.contentResolver
    val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.ALBUM_ID
    )

    val selection = "${MediaStore.Audio.Media.DURATION} > 0"

    Log.d("MP3_DEBUG", "Starting MediaStore query for MP3 files...")

    val cursor = contentResolver.query(uri, projection, selection, null, null)
    cursor?.use { cursor ->
        val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
        val titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
        val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
        val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
        val albumIdColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)

        if (idColumn == -1 || titleColumn == -1 || artistColumn == -1 || durationColumn == -1 || albumIdColumn == -1) {
            Log.e("MP3_DEBUG", "One or more MediaStore columns not found. Check projection.")
            return emptyList()
        }


        var songCount = 0
        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val title = cursor.getString(titleColumn)
            val artist = cursor.getString(artistColumn)
            val duration = cursor.getLong(durationColumn)
            val albumId = cursor.getLong(albumIdColumn)

            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                id
            )

            val song = Song(id, title, artist, duration, albumId, contentUri)
            songs.add(song)
            songCount++

            Log.d(
                "MP3_DEBUG",
                "Found Song: ID=$id, Title='$title', Artist='$artist', Duration=$duration, AlbumID=$albumId, URI=$contentUri"
            )
        }

        if (songCount == 0) {
            Log.d("MP3_DEBUG", "No audio files found in MediaStore after iterating.")
        } else {
            Log.d("MP3_DEBUG", "Finished MediaStore query. Total songs found: $songCount")
        }

    }
    return songs
}

fun getAlbumArtUri(albumId: Long): Uri {
    return ContentUris.withAppendedId(
        "content://media/external/audio/albumart".toUri(),
        albumId
    )
}

fun formatDuration(durationMillis: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis) -
            TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%02d:%02d", minutes, seconds)
}

fun getEmbeddedThumbnail(songUri: Uri, context: Context): Bitmap? {
    val retriever = MediaMetadataRetriever()
    try {
        retriever.setDataSource(context, songUri)
        val embeddedPic = retriever.embeddedPicture
        if (embeddedPic != null) {
            return BitmapFactory.decodeByteArray(embeddedPic, 0, embeddedPic.size)
        }
    } catch (e: Exception) {
        Log.e("MP3_DEBUG", "Error getting embedded thumbnail for $songUri: ${e.message}")
    } finally {
        try {
            retriever.release()
        } catch (e: Exception) {
            Log.e("MP3_DEBUG", "Error releasing MediaMetadataRetriever: ${e.message}")
        }
    }
    return null
}