package com.example.buibinhminh.repository

import com.example.buibinhminh.database.dao.ProfileDao
import com.example.buibinhminh.database.entity.ProfileEntity

class ProfileRepository(private val profileDao: ProfileDao) {
    suspend fun getProfileByUserId(userId: Int): ProfileEntity? {
        return profileDao.getProfileByUserId(userId)
    }

    suspend fun saveProfile(profile: ProfileEntity) : Long {
        return profileDao.insertOrUpdateProfile(profile)
    }
}