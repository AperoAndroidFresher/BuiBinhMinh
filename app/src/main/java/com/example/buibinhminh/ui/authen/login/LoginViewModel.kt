package com.example.buibinhminh.ui.authen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.data.User
import com.example.buibinhminh.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(LoginState())
    val viewState: StateFlow<LoginState> = _viewState.asStateFlow()

    private val _loginEvent = MutableSharedFlow<LoginEvent>()
    val loginEvent: SharedFlow<LoginEvent> = _loginEvent.asSharedFlow()

    fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.UpdateUsername -> {
                _viewState.update { it.copy(username = intent.username, errorMessage = null) }
            }

            is LoginIntent.UpdatePassword -> {
                _viewState.update { it.copy(password = intent.password, errorMessage = null) }
            }

            LoginIntent.TogglePasswordVisibility -> {
                _viewState.update { it.copy(passwordVisible = !it.passwordVisible) }
            }

            LoginIntent.LoginClicked -> {
                performLogin()
            }

            LoginIntent.SignUpClicked -> {
                viewModelScope.launch {
                    _loginEvent.emit(LoginEvent.NavigateToSignUpScreen)
                }
            }
        }
    }

    private fun performLogin() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true, errorMessage = null) }

            val currentState = _viewState.value
            val username = currentState.username
            val password = currentState.password

            delay(1000)

            val user = userRepository.getUserByCredentials(username, password)

            if (user != null) {
                _viewState.update { it.copy(isLoading = false, isLoginSuccess = true) }
                _loginEvent.emit(LoginEvent.ShowToast("Login successful!"))
                _loginEvent.emit(LoginEvent.NavigateToHomeScreen)
            } else {
                _viewState.update { it.copy(isLoading = false, errorMessage = "Invalid username or password") }
                _loginEvent.emit(LoginEvent.ShowToast("Login failed: Invalid username or password"))
            }
        }
    }
}
