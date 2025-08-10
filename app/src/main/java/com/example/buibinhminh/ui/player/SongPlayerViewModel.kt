package com.example.buibinhminh.ui.player

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.data.Song
import com.example.buibinhminh.service.MediaPlayerService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class SongPlayerViewModel (application: Application) : AndroidViewModel(application) {
    private val _nowPlayingState = MutableStateFlow(SongPlayerState())
    val nowPlayingState: StateFlow<SongPlayerState> = _nowPlayingState.asStateFlow()

    private var mediaPlayerService: WeakReference<MediaPlayerService>? = null
    private var isServiceBound = false
    private var progressJob: Job? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MediaPlayerService.MusicBinder
            mediaPlayerService = WeakReference(binder.getService())
            isServiceBound = true
            startProgressUpdate()
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceBound = false
            mediaPlayerService = null
            progressJob?.cancel()
        }
    }

    init {
        val intent = Intent(application, MediaPlayerService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            application.startForegroundService(intent)
        } else {
            application.startService(intent)
        }
        application.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onCleared() {
        super.onCleared()
        if (isServiceBound) {
            getApplication<Application>().unbindService(serviceConnection)
            isServiceBound = false
        }
        progressJob?.cancel()
    }

    fun playSong(song: Song) {
        mediaPlayerService?.get()?.playSong(song)
        _nowPlayingState.update { it.copy(nowPlayingSong = song, isPlaying = true) }
    }

    fun seekTo(position: Long) {
        mediaPlayerService?.get()?.seekTo(position.toInt())
    }

    fun pauseSong() {
        mediaPlayerService?.get()?.pauseSong()
        _nowPlayingState.update { it.copy(isPlaying = false) }
    }

    fun resumeSong() {
        mediaPlayerService?.get()?.resumeSong()
        _nowPlayingState.update { it.copy(isPlaying = true) }
    }

    private fun startProgressUpdate() {
        progressJob?.cancel()
        progressJob = viewModelScope.launch {
            while (isServiceBound) {
                val service = mediaPlayerService?.get()
                val currentPosition = service?.getCurrentPosition()?.toLong() ?: 0L
                val totalDuration = service?.getDuration()?.toLong() ?: 1L
                val progress = if (totalDuration > 0) currentPosition.toFloat() / totalDuration.toFloat() else 0f

                _nowPlayingState.update {
                    it.copy(
                        currentTime = currentPosition,
                        songProgress = progress
                    )
                }
                delay(1000L)
            }
        }
    }
}