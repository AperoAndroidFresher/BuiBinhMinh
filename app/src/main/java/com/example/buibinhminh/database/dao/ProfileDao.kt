package com.example.buibinhminh.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.buibinhminh.database.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: ProfileEntity)

    @Query("SELECT * FROM profile WHERE userId = :userId LIMIT 1")
    suspend fun getProfileByUserId(userId: Int): ProfileEntity?
}