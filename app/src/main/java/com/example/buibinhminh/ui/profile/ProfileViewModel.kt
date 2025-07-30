package com.example.buibinhminh.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.data.Student
import com.example.buibinhminh.ui.theme.ThemeType
import com.example.buibinhminh.ui.theme.darkTheme
import com.example.buibinhminh.ui.theme.lightTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _viewState = MutableStateFlow(ProfileState())
    val viewState: StateFlow<ProfileState> = _viewState.asStateFlow()

    private val _profileEvent = MutableSharedFlow<ProfileEvent>()
    val profileEvent: SharedFlow<ProfileEvent> = _profileEvent.asSharedFlow()

    fun processIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.ToggleEditMode -> {
                _viewState.update { it.copy(isEditing = !it.isEditing, showSuccessDialog = false) }
            }
            ProfileIntent.ToggleTheme -> {
                _viewState.update {
                    it.copy(
                        currentTheme = when (it.currentTheme.type) {
                            ThemeType.THEME1 -> lightTheme
                            ThemeType.THEME2 -> darkTheme
                        }
                    )
                }
            }
            is ProfileIntent.UpdateProfileImage -> {
                _viewState.update { it.copy(profileImageUri = intent.uri) }
            }
            is ProfileIntent.UpdateName -> {
                _viewState.update { it.copy(name = intent.name) }
            }
            is ProfileIntent.UpdatePhoneNumber -> {
                _viewState.update { it.copy(phoneNumber = intent.phoneNumber) }
            }
            is ProfileIntent.UpdateUniversityName -> {
                _viewState.update { it.copy(universityName = intent.universityName) }
            }
            is ProfileIntent.UpdateDescription -> {
                _viewState.update { it.copy(description = intent.description) }
            }
            is ProfileIntent.SetNameError -> {
                _viewState.update { it.copy(isNameError = intent.hasError) }
            }
            is ProfileIntent.SetPhoneNumberError -> {
                _viewState.update { it.copy(isPhoneNumberError = intent.hasError) }
            }
            is ProfileIntent.SetUniversityNameError -> {
                _viewState.update { it.copy(isUniversityNameError = intent.hasError) }
            }
            ProfileIntent.SubmitProfile -> {
                viewModelScope.launch {
                    _viewState.update { it.copy(isLoading = true) }
                    delay(1000)

                    val currentStudent = Student(
                        name = _viewState.value.name,
                        phoneNumber = _viewState.value.phoneNumber,
                        universityName = _viewState.value.universityName,
                        description = _viewState.value.description
                    )
                    println("Profile Created")
                    println(currentStudent)

                    _viewState.update { it.copy(isLoading = false) }
                    _profileEvent.emit(ProfileEvent.ShowSuccessMessage)
                }
            }
            ProfileIntent.DismissSuccessDialog -> {
                _viewState.update { it.copy(showSuccessDialog = false, isEditing = false) }
            }
        }
    }

    fun showSuccessDialog() {
        _viewState.update { it.copy(showSuccessDialog = true) }
        viewModelScope.launch {
            delay(2000)
            processIntent(ProfileIntent.DismissSuccessDialog)
        }
    }
}