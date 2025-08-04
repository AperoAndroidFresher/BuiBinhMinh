package com.example.buibinhminh.ui.authen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.data.User
import com.example.buibinhminh.database.entity.UserEntity
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

class SignUpViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(SignUpState())
    val viewState: StateFlow<SignUpState> = _viewState.asStateFlow()

    private val _signUpEvent = MutableSharedFlow<SignUpEvent>()
    val signUpEvent: SharedFlow<SignUpEvent> = _signUpEvent.asSharedFlow()

    fun processIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.UpdateUsername -> _viewState.update { it.copy(username = intent.username, usernameError = null) }
            is SignUpIntent.UpdatePassword -> _viewState.update { it.copy(password = intent.password, passwordError = null) }
            is SignUpIntent.UpdatePasswordConfirmed -> _viewState.update { it.copy(passwordConfirmed = intent.passwordConfirmed, confirmPasswordError = null) }
            is SignUpIntent.UpdateEmail -> _viewState.update { it.copy(email = intent.email, emailError = null) }
            SignUpIntent.TogglePasswordVisibility -> _viewState.update { it.copy(passwordVisible = !it.passwordVisible) }
            SignUpIntent.SignUpClicked -> validateAndSignUp()
        }
    }

    private fun validateAndSignUp() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }

            val currentState = _viewState.value

            var usernameError: String? = null
            var passwordError: String? = null
            var confirmPasswordError: String? = null
            var emailError: String? = null

            if (!currentState.username.matches("^[a-zA-Z0-9]+$".toRegex())) {
                usernameError = "Invalid format"
            }
            if (!currentState.password.matches("^[a-zA-Z0-9]+$".toRegex())) {
                passwordError = "Invalid format"
            }
            if (currentState.password != currentState.passwordConfirmed) {
                confirmPasswordError = "Confirmation password does not match"
            }
            if (!currentState.email.matches("^[a-zA-Z0-9._-]+@apero\\.vn$".toRegex())) {
                emailError = "Invalid format"
            }
            if (usernameError == null && userRepository.getUserByUsername(currentState.username) != null) {
                usernameError = "Username already exists."
            }
            if (emailError == null && userRepository.getUserByEmail(currentState.email) != null) {
                emailError = "Email already exists."
            }

            _viewState.update {
                it.copy(
                    usernameError = usernameError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError,
                    emailError = emailError
                )
            }

            if (usernameError == null && passwordError == null && confirmPasswordError == null && emailError == null) {
                delay(1000)

                val newUserEntity = UserEntity(
                    username = currentState.username,
                    email = currentState.email,
                    password = currentState.password
                )
                userRepository.insertUser(newUserEntity)
                _viewState.update { it.copy(isLoading = false) }
                _signUpEvent.emit(SignUpEvent.ShowToast("Sign up successful!"))
                _signUpEvent.emit(SignUpEvent.NavigateToLoginScreen(newUserEntity))

            } else {
                _viewState.update { it.copy(isLoading = false) }
                _signUpEvent.emit(SignUpEvent.ShowToast("Please correct the errors."))
            }
        }
    }
}