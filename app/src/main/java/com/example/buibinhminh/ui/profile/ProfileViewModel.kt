package com.example.buibinhminh.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buibinhminh.data.Student
import com.example.buibinhminh.database.entity.ProfileEntity
import com.example.buibinhminh.repository.ProfileRepository
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

class ProfileViewModel(
    private val userId: Int,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(ProfileState())
    val viewState: StateFlow<ProfileState> = _viewState.asStateFlow()

    private val _profileEvent = MutableSharedFlow<ProfileEvent>()
    val profileEvent: SharedFlow<ProfileEvent> = _profileEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            val profile = profileRepository.getProfileByUserId(userId)
            if (profile != null) {
                _viewState.update {
                    it.copy(
                        name = profile.name,
                        phoneNumber = profile.phoneNumber,
                        universityName = profile.universityName,
                        description = profile.description,
                        profileImageUri = profile.imageUri
                    )
                }
            }
        }
    }

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

                    val currentProfile = _viewState.value
                    val profileToSave = ProfileEntity(
                        userId = userId,
                        name = currentProfile.name,
                        phoneNumber = currentProfile.phoneNumber,
                        universityName = currentProfile.universityName,
                        description = currentProfile.description,
                        imageUri = currentProfile.profileImageUri
                    )

                    profileRepository.saveProfile(profileToSave)

                    _viewState.update { it.copy(isLoading = false, isEditing = false) }
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