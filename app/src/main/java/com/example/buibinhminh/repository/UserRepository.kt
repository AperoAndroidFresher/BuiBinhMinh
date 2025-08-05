package com.example.buibinhminh.repository

import com.example.buibinhminh.database.dao.UserDao
import com.example.buibinhminh.database.entity.UserEntity

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: UserEntity): Long {
        return userDao.insertUser(user)
    }

    suspend fun getUserByCredentials(username: String, password: String): UserEntity? {
        return userDao.getUserByCredentials(username, password)
    }

    suspend fun getUserByUsername(username: String): UserEntity? {
        return userDao.getUserByUsername(username)
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }
}