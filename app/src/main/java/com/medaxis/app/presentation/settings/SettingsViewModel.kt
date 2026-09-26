package com.medaxis.app.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.medaxis.app.MedaxisApplication
import com.medaxis.app.data.local.profile.UserProfileRepository
import kotlinx.coroutines.launch

/** ViewModel for the Settings screen. Provides a method to clear the stored user profile. */
class SettingsViewModel(private val repository: UserProfileRepository) : ViewModel() {
    /** Clears the locally stored user profile. */
    fun clearProfile() {
        viewModelScope.launch {
            repository.clearProfile()
        }
    }

    companion object {
        /** Factory for creating the SettingsViewModel with the required repository. */
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val context = MedaxisApplication.instance
                return SettingsViewModel(UserProfileRepository(context)) as T
            }
        }
    }
}
