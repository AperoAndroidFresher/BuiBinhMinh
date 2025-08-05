package com.example.buibinhminh.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.buibinhminh.database.entity.PlaylistEntity
import com.example.buibinhminh.database.entity.PlaylistSongEntity
import com.example.buibinhminh.database.relationships.PlaylistSongs
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Insert
    suspend fun insertPlaylist(playlist: PlaylistEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistSong(crossRef: PlaylistSongEntity)

    @Delete
    suspend fun deletePlaylistSong(crossRef: PlaylistSongEntity)

    @Transaction
    @Query("SELECT * FROM playlists WHERE id = :playlistId")
    fun getPlaylistWithSongs(playlistId: Long): Flow<PlaylistSongs>

    @Query("SELECT * FROM playlists WHERE userId = :userId")
    fun getPlaylistsForUser(userId: Int): Flow<List<PlaylistEntity>>

    @Transaction
    @Query("SELECT * FROM playlists WHERE userId = :userId")
    fun getPlaylistsWithSongsForUser(userId: Int): Flow<List<PlaylistSongs>>

    @Query("UPDATE playlists SET name = :newName WHERE id = :playlistId")
    suspend fun updatePlaylistName(playlistId: Long, newName: String)

    @Query("DELETE FROM playlists WHERE id = :playlistId")
    suspend fun deletePlaylist(playlistId: Long)
}