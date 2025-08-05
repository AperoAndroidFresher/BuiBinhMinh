package com.example.buibinhminh.repository

import com.example.buibinhminh.database.dao.PlaylistDao
import com.example.buibinhminh.database.dao.SongDao
import com.example.buibinhminh.database.entity.PlaylistEntity
import com.example.buibinhminh.database.entity.PlaylistSongEntity
import com.example.buibinhminh.database.entity.SongEntity
import com.example.buibinhminh.database.relationships.PlaylistSongs
import kotlinx.coroutines.flow.Flow

class PlaylistRepository(
    private val playlistDao: PlaylistDao,
    private val songDao: SongDao
) {
    fun getPlaylistWithSongs(playlistId: Long): Flow<PlaylistSongs> {
        return playlistDao.getPlaylistWithSongs(playlistId)
    }

    fun getPlaylistsWithSongsForUser(userId: Int): Flow<List<PlaylistSongs>> {
        return playlistDao.getPlaylistsWithSongsForUser(userId)
    }

    suspend fun addSongToPlaylist(playlistId: Long, songId: Long) {
        val crossRef = PlaylistSongEntity(playlistId, songId)
        playlistDao.insertPlaylistSong(crossRef)
    }

    suspend fun saveSong(song: SongEntity) {
        songDao.insertSong(song)
    }

    suspend fun removeSongFromPlaylist(playlistId: Long, songId: Long) {
        val crossRef = PlaylistSongEntity(playlistId, songId)
        playlistDao.deletePlaylistSong(crossRef)
    }

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