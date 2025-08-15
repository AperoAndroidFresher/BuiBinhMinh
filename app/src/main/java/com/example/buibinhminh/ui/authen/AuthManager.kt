package com.example.buibinhminh.ui.authen

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AuthManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_auth", Context.MODE_PRIVATE)

    companion object {
        private const val USER_ID_KEY = "user_id_key"
        private const val USER_NAME_KEY = "user_name_key"
        private const val USER_PASSWORD_KEY = "user_password_key"
    }

    fun saveUser(userId: Int, username: String, password: String) {
        sharedPreferences.edit().apply {
            putInt(USER_ID_KEY, userId)
            putString(USER_NAME_KEY, username)
            putString(USER_PASSWORD_KEY, password)
            apply()
        }
    }

    fun getSavedUserId(): Int? {
        val id = sharedPreferences.getInt(USER_ID_KEY, -1)
        return if (id == -1) null else id
    }

    fun clearUser() {
        sharedPreferences.edit { clear() }
    }
}