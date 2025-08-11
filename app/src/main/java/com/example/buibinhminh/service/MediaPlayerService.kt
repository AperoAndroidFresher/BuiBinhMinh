package com.example.buibinhminh.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.compose.ui.res.painterResource
import androidx.core.app.NotificationCompat
import coil.compose.rememberAsyncImagePainter
import com.example.buibinhminh.MainActivity
import com.example.buibinhminh.R
import com.example.buibinhminh.data.Song
import com.example.buibinhminh.helper.getEmbeddedThumbnail
import kotlin.jvm.java

class MediaPlayerService : Service() {
    private val binder = MusicBinder()
    private var mediaPlayer: MediaPlayer? = null
    private var currentSong: Song? = null

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "MusicPlayerChannel"
        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_RESUME = "ACTION_RESUME"
        const val ACTION_SKIP_NEXT = "ACTION_SKIP_NEXT"
        const val ACTION_SKIP_PREVIOUS = "ACTION_SKIP_PREVIOUS"
        const val ACTION_STOP = "ACTION_STOP"
    }

    inner class MusicBinder : Binder() {
        fun getService(): MediaPlayerService = this@MediaPlayerService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PAUSE -> pauseSong()
            ACTION_RESUME -> resumeSong()
            ACTION_SKIP_NEXT -> {}
            ACTION_SKIP_PREVIOUS -> {}
            ACTION_STOP -> {
                mediaPlayer?.release()
                stopForeground(STOP_FOREGROUND_REMOVE)
            }
        }
        return START_NOT_STICKY
    }

    fun playSong(song: Song) {
        if (currentSong?.id == song.id && mediaPlayer?.isPlaying == true) return

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(this@MediaPlayerService, song.contentUri)
            prepareAsync()
            setOnPreparedListener { mp ->
                mp.start()
                currentSong = song
                startForeground(NOTIFICATION_ID, createNotification(true))
            }
        }
    }

    fun pauseSong() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            updateNotification(false)
        }
    }

    fun resumeSong() {
        if (mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
            updateNotification(true)
        }
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    fun isPlaying(): Boolean = mediaPlayer?.isPlaying == true

    fun getCurrentPosition(): Int = mediaPlayer?.currentPosition ?: 0

    fun getDuration(): Int = mediaPlayer?.duration ?: 0


    private fun createNotification(isPlaying: Boolean): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Player",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Thông báo điều khiển trình phát nhạc"
            }
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val playPauseIcon =
            if (isPlaying) R.drawable.round_pause_24 else R.drawable.baseline_play_arrow_24
        val playPauseAction = if (isPlaying) ACTION_PAUSE else ACTION_RESUME

        val songTitle = currentSong?.title ?: "Unknown title"
        val songArtist = currentSong?.artist ?: "Unknown artist"
        val thumbnailBitmap = currentSong?.let {
            getEmbeddedThumbnail(it.contentUri, this)
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_music_note_24)
            .setContentTitle(songTitle)
            .setContentText(songArtist)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(null)
            )
            .addAction(
                R.drawable.baseline_skip_previous_24,
                "Previous",
                getPendingIntent(ACTION_SKIP_PREVIOUS)
            )
            .addAction(
                playPauseIcon,
                if (isPlaying) "Pause" else "Resume",
                getPendingIntent(playPauseAction)
            )
            .addAction(R.drawable.baseline_skip_next_24, "Next", getPendingIntent(ACTION_SKIP_NEXT))
            .addAction(R.drawable.outline_close_24, "Close", getPendingIntent(ACTION_STOP))
            .setDeleteIntent(getPendingIntent(ACTION_STOP))

        if (thumbnailBitmap != null) {
            builder.setLargeIcon(thumbnailBitmap)
        }

        return builder.build()
    }

    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, MediaPlayerService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun updateNotification(isPlaying: Boolean) {
        val notification = createNotification(isPlaying)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

}