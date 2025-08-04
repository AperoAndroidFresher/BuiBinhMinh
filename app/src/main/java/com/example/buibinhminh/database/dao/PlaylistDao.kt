package com.example.buibinhminh.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.buibinhminh.database.entity.PlaylistEntity
import com.example.buibinhminh.database.entity.PlaylistSongEntity
import com.example.buibinhminh.database.relationships.PlaylistSongs

@Dao
interface PlaylistDao {
    @Insert
    suspend fun insertPlaylist(playlist: PlaylistEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistSong(crossRef: PlaylistSongEntity)

    @Transaction
    @Query("SELECT * FROM playlists WHERE id = :playlistId")
    suspend fun getPlaylistWithSongs(playlistId: Int): PlaylistSongs

    @Transaction
    @Query("SELECT * FROM playlists WHERE userId = :userId")
    suspend fun getPlaylistsForUser(userId: Int): List<PlaylistSongs>
}