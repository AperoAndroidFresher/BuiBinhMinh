package com.example.buibinhminh.ui.authen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.database.entity.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//class AuthViewModel : ViewModel() {
//    private val _currentUser = MutableStateFlow<UserEntity?>(null)
//    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()
//
//    fun setLoggedInUser(user: UserEntity) {
//        _currentUser.value = user
//    }
//
//    fun logout() {
//        _currentUser.value = null
//    }
//}

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val authManager = AuthManager(application)

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    init {
        viewModelScope.launch {
            val userId = authManager.getSavedUserId()
            if (userId != null) {
                _currentUser.value = UserEntity(
                    id = userId,
                    username = "",
                    password = "",
                    email = ""
                )
            }
        }
    }

    fun setLoggedInUser(user: UserEntity) {
        viewModelScope.launch {
            authManager.saveUser(user.id, user.username, user.password)
            _currentUser.value = user
        }
    }

    fun logout() {
        viewModelScope.launch {
            authManager.clearUser()
            _currentUser.value = null
        }
    }
}