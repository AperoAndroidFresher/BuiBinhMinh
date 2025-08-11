package com.example.buibinhminh.ui.player

import com.example.buibinhminh.data.Song
import kotlin.random.Random

enum class RepeatMode {
    OFF,
    REPEAT_ALL,
    REPEAT_ONE
}

class PlaybackQueueManager {
    private var originalQueue: List<Song> = emptyList()
    private var currentQueue: List<Song> = emptyList()
    private var currentIndex: Int = -1

    var isShuffling: Boolean = false
        private set

    var repeatMode: RepeatMode = RepeatMode.OFF
        private set

    fun setQueue(songs: List<Song>, startIndex: Int) {
        originalQueue = songs
        currentQueue = songs
        currentIndex = startIndex

        if (isShuffling) {
            shuffleQueue()
        }
    }

    fun toggleShuffle(): Boolean {
        isShuffling = !isShuffling
        if (isShuffling) {
            shuffleQueue()
        } else {
            resetQueue()
        }
        return isShuffling
    }

    private fun shuffleQueue() {
        val currentSong = currentQueue.getOrNull(currentIndex)
        val shuffledList = originalQueue.shuffled(Random)
        currentQueue = shuffledList

        currentIndex = currentQueue.indexOf(currentSong)
        if (currentIndex == -1) {
            currentIndex = 0
        }
    }

    fun getNowPlayingSong(): Song? {
        return currentQueue.getOrNull(currentIndex)
    }

    fun skipToNext(): Song? {
        if (currentQueue.isEmpty()) return null

        if (repeatMode == RepeatMode.REPEAT_ONE) {
            return currentQueue.getOrNull(currentIndex)
        }

        if (repeatMode == RepeatMode.REPEAT_ALL) {
            currentIndex = (currentIndex + 1) % currentQueue.size
            return currentQueue.getOrNull(currentIndex)
        }

        if (currentIndex == currentQueue.size - 1) {
            return null
        }

        currentIndex++
        return currentQueue.getOrNull(currentIndex)
    }

    fun skipToPrevious(): Song? {
        if (currentQueue.isEmpty()) return null

        if (repeatMode == RepeatMode.REPEAT_ONE) {
            return currentQueue.getOrNull(currentIndex)
        }

        if (repeatMode == RepeatMode.REPEAT_ALL) {
            currentIndex = if (currentIndex > 0) currentIndex - 1 else currentQueue.size - 1
            return currentQueue.getOrNull(currentIndex)
        }

        if (currentIndex == 0) {
            return null
        }

        currentIndex = if (currentIndex > 0) currentIndex - 1 else currentQueue.size - 1
        return currentQueue.getOrNull(currentIndex)
    }

    private fun resetQueue() {
        val currentSong = currentQueue.getOrNull(currentIndex)
        currentQueue = originalQueue

        currentIndex = currentQueue.indexOf(currentSong)
        if (currentIndex == -1) {
            currentIndex = 0
        }
    }

    fun toggleRepeat(): RepeatMode {
        repeatMode = when (repeatMode) {
            RepeatMode.OFF -> RepeatMode.REPEAT_ALL
            RepeatMode.REPEAT_ALL -> RepeatMode.REPEAT_ONE
            RepeatMode.REPEAT_ONE -> RepeatMode.OFF
        }
        return repeatMode
    }
}