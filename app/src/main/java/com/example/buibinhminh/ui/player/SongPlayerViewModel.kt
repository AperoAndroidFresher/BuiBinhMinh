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

    private val playbackQueueManager = PlaybackQueueManager()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MediaPlayerService.MusicBinder

            mediaPlayerService = WeakReference(binder.getService())
            isServiceBound = true

            binder.setPlayerListeners(
                onSkipNext = { processIntent(SongPlayerIntent.SkipNext) },
                onSkipPrevious = { processIntent(SongPlayerIntent.SkipPrevious) },
                onSongCompletion = { processIntent(SongPlayerIntent.SongFinished) },
                onServiceClosed = { processIntent(SongPlayerIntent.CloseSong) },
                onSongPaused = { processIntent(SongPlayerIntent.PauseSong) },
                onSongResumed = { processIntent(SongPlayerIntent.ResumeSong) }
            )

            startProgressUpdate()
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceBound = false
            mediaPlayerService = null
            progressJob?.cancel()

            _nowPlayingState.update { SongPlayerState() }
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

    fun processIntent(intent: SongPlayerIntent) {
        when (intent) {
            is SongPlayerIntent.PlaySong -> {
                val song = intent.song
                mediaPlayerService?.get()?.playSong(song)
                _nowPlayingState.update { it.copy(nowPlayingSong = song, isPlaying = true) }
            }

            is SongPlayerIntent.SetQueueAndPlay -> {
                val songs = intent.songs
                val startSong = intent.startSong
                val startIndex = songs.indexOf(startSong)

                playbackQueueManager.setQueue(songs, startIndex)

                processIntent(SongPlayerIntent.PlaySong(startSong))
            }

            SongPlayerIntent.PauseSong -> {
                mediaPlayerService?.get()?.pauseSong()
                _nowPlayingState.update { it.copy(isPlaying = false) }
            }

            SongPlayerIntent.ResumeSong -> {
                mediaPlayerService?.get()?.resumeSong()
                _nowPlayingState.update { it.copy(isPlaying = true) }
            }

            is SongPlayerIntent.SeekTo -> {
                mediaPlayerService?.get()?.seekTo(intent.position.toInt())
            }

            SongPlayerIntent.SkipNext -> {
                val nextSong = playbackQueueManager.skipToNext()
                if (nextSong != null) {
                    processIntent(SongPlayerIntent.PlaySong(nextSong))
                }
            }

            SongPlayerIntent.SkipPrevious -> {
                val previousSong = playbackQueueManager.skipToPrevious()
                if (previousSong != null) {
                    processIntent(SongPlayerIntent.PlaySong(previousSong))
                }
            }

            SongPlayerIntent.SongFinished -> {
                val nextSong = playbackQueueManager.skipToNext()
                if (nextSong != null) {
                    processIntent(SongPlayerIntent.PlaySong(nextSong))
                } else {
                    mediaPlayerService?.get()?.pauseSong()
                    _nowPlayingState.update { it.copy(isPlaying = false) }
                }
            }

            SongPlayerIntent.ToggleShuffle -> {
                val isShuffling = playbackQueueManager.toggleShuffle()
                _nowPlayingState.update { it.copy(isShuffling = isShuffling) }
                val nowPlaying = playbackQueueManager.getNowPlayingSong()
                _nowPlayingState.update { it.copy(nowPlayingSong = nowPlaying) }
            }

            SongPlayerIntent.ToggleRepeat -> {
                val newRepeatMode = playbackQueueManager.toggleRepeat()
                _nowPlayingState.update { it.copy(repeatMode = newRepeatMode) }
            }

            SongPlayerIntent.CloseSong -> {
                mediaPlayerService?.get()?.stopSong()

                _nowPlayingState.update {
                    SongPlayerState(
                        nowPlayingSong = null,
                        isPlaying = false,
                        isShuffling = false,
                        repeatMode = RepeatMode.OFF
                    )
                }
            }
        }
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
                delay(500L)
            }
        }
    }
}