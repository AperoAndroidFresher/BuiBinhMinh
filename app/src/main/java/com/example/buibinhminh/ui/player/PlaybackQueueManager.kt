package com.example.buibinhminh.ui.player

import com.example.buibinhminh.data.Song

class PlaybackQueueManager {

    private var currentQueue: List<Song> = emptyList()
    private var currentIndex: Int = -1

    fun setQueue(songs: List<Song>, startIndex: Int) {
        currentQueue = songs
        currentIndex = startIndex
    }

    fun getNowPlayingSong(): Song? {
        return currentQueue.getOrNull(currentIndex)
    }

    fun skipToNext(): Song? {
        if (currentQueue.isEmpty()) return null

        currentIndex = (currentIndex + 1) % currentQueue.size
        return currentQueue.getOrNull(currentIndex)
    }

    fun skipToPrevious(): Song? {
        if (currentQueue.isEmpty()) return null

        currentIndex = if (currentIndex > 0) currentIndex - 1 else currentQueue.size - 1
        return currentQueue.getOrNull(currentIndex)
    }
}