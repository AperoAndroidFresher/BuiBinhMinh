package com.example.buibinhminh.repository

import com.example.buibinhminh.database.dao.PlaylistDao
import com.example.buibinhminh.database.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

class PlaylistRepository(private val playlistDao: PlaylistDao) {

    fun getPlaylistsForUser(userId: Int): Flow<List<PlaylistEntity>> {
        return playlistDao.getPlaylistsForUser(userId)
    }

    suspend fun insertPlaylist(playlist: PlaylistEntity): Long {
        return playlistDao.insertPlaylist(playlist)
    }

    suspend fun updatePlaylistName(playlistId: Long, newName: String) {
        playlistDao.updatePlaylistName(playlistId, newName)
    }

    suspend fun deletePlaylist(playlistId: Long) {
        playlistDao.deletePlaylist(playlistId)
    }
}