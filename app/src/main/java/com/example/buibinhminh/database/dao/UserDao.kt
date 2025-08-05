package com.example.buibinhminh.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.buibinhminh.database.entity.ProfileEntity
import com.example.buibinhminh.database.entity.UserEntity
import com.example.buibinhminh.database.relationships.UserPlaylists
import com.example.buibinhminh.database.relationships.UserProfile

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity): Long

    @Insert
    suspend fun insertProfile(profile: ProfileEntity)

    @Query("SELECT * FROM users WHERE user_name = :username AND password = :password")
    suspend fun getUserByCredentials(username: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE user_name = :username")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithProfile(userId: Int): UserProfile?

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithPlaylists(userId: Int): UserPlaylists?
}